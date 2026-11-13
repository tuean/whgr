package com.tuean.whgr.func.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tuean.whgr.dao.AiLogMapper;
import com.tuean.whgr.dao.MenusMapper;
import com.tuean.whgr.entity.BaseListRequest;
import com.tuean.whgr.entity.ListResponse;
import com.tuean.whgr.entity.MineResp;
import com.tuean.whgr.entity.db.AiLog;
import com.tuean.whgr.entity.db.Menus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

/**
 * AI 入口：用户一句话 → 模型通过工具（list_menus / navigate）决定去哪个页面 → 边跑边把
 * 「思考 / 工具调用 / 跳转」事件推给前端，最后落一份 ai_log 供回溯。
 *
 * 和 agent/ 里的编码 agent 是同一套架构（agent loop + JSON 工具协议 + 工具异常回喂模型），
 * 但这里只暴露纯数据动作（读菜单、记跳转），碰不到文件系统和命令。
 *
 * 权限：菜单是按当前账号角色过滤的，navigate 的 path 必须命中过滤后的白名单，
 * 所以 AI 不可能把人送到他没有权限的页面。
 */
@Service
public class AiService {

    private static final Logger logger = LoggerFactory.getLogger(AiService.class);

    private static final String SYSTEM_PROMPT =
            "你是 whgr 后台管理系统唯一的入口。用户用自然语言说他想做什么，你判断该去哪个页面。\n"
            + "可用动作：\n"
            + "1. list_menus：列出当前用户有权访问的菜单（name + path）。不确定有哪些页面时先调它。\n"
            + "2. navigate：跳转页面，path 必须来自 list_menus 的返回。\n"
            + "规则：\n"
            + "- 需求能对应到某个菜单时：先 list_menus 确认 path，再 navigate。\n"
            + "- 只是提问、闲聊、问系统能力时：直接用一句话回答，不要调工具。\n"
            + "- 用户要的功能不在菜单里（可能没权限或没做）：直说没有，并列出最接近的菜单，不要编造 path。\n"
            + "- 每次调工具前，先用一句话说明你要做什么（这句话会展示给用户，作为决策依据）。\n"
            + "- 只用中文回答，纯文本，不要用 Markdown 语法；跳转后用一句话说明你做了什么。";

    @Autowired
    private MenusMapper menusMapper;

    @Autowired
    private AiLogMapper aiLogMapper;

    @Autowired
    private AiLlmClient llm;

    @Autowired
    private ObjectMapper json;

    @Value("${ai.enabled:true}")
    private boolean enabled;

    @Value("${ai.max-steps:5}")
    private int maxSteps;

    /** 过程事件回调，payload 里带 type；controller 直接转 SSE，前端按 type 渲染时间线 */
    public interface Listener {
        void onEvent(String type, ObjectNode payload);
    }

    /**
     * 跑一轮问答。过程事件实时回调给 listener，结束后写 ai_log 并返回日志 id。
     */
    public Long chat(String message, Long adminId, Integer role, Listener listener) {
        if (!enabled) throw new IllegalStateException("AI 入口已关闭（ai.enabled=false）");
        if (message == null || message.trim().isEmpty()) throw new IllegalArgumentException("问题不能为空");
        if (!llm.configured()) throw new IllegalStateException("AI 未配置：设置环境变量 DEEPSEEK_API_KEY 或 ai.api-key 后重启后端");

        String question = message.trim();
        List<Menus> menus = menusMapper.selectByRole(role == null ? 2 : role);
        List<AiAction> actions = new ArrayList<>();
        ArrayNode trace = json.createArrayNode();

        List<ObjectNode> messages = new ArrayList<>();
        messages.add(text("system", SYSTEM_PROMPT));
        messages.add(text("user", question));

        String reply = null;
        int step = 0;
        while (step < Math.max(1, maxSteps) && reply == null) {
            step++;
            JsonNode assistant = llm.chat(messages, toolDefs());
            messages.add((ObjectNode) assistant);

            String thinking = assistant.path("reasoning_content").asText("");
            if (!thinking.trim().isEmpty()) {
                push(listener, trace, event("thinking", step).put("text", thinking));
            }

            JsonNode toolCalls = assistant.path("tool_calls");
            if (!toolCalls.isArray() || toolCalls.size() == 0) {
                reply = assistant.path("content").asText("");
                break;
            }

            // 模型调工具前自己说的话 = 决策依据，展示给用户
            String decision = assistant.path("content").asText("");
            if (!decision.trim().isEmpty()) {
                push(listener, trace, event("decision", step).put("text", decision));
            }

            for (JsonNode call : toolCalls) {
                String name = call.path("function").path("name").asText();
                String argsText = call.path("function").path("arguments").asText("{}");
                String result = handleTool(step, name, argsText, menus, actions, listener, trace);

                ObjectNode toolMessage = json.createObjectNode();
                toolMessage.put("role", "tool");
                toolMessage.put("tool_call_id", call.path("id").asText());
                toolMessage.put("content", result);
                messages.add(toolMessage);
            }
        }
        if (reply == null) reply = "我不太确定该跳到哪个页面，能说得再具体一点吗？";

        push(listener, trace, event("answer", step).put("reply", reply));

        AiLog log = new AiLog();
        log.setAdminId(adminId);
        log.setMessage(question.length() > 400 ? question.substring(0, 400) : question);
        log.setReply(reply);
        log.setActions(write(actions));
        log.setTrace(trace.toString());
        log.setCreateTime(new Date());
        aiLogMapper.insert(log);
        logger.info("ai chat admin={} steps={} actions={} logId={}", adminId, step, actions.size(), log.getId());
        return log.getId();
    }

    /** 我的历史问答（回溯用） */
    public MineResp history(BaseListRequest request, Long adminId) {
        int total = aiLogMapper.countByAdmin(adminId);
        if (total < 1) return MineResp.success(ListResponse.empty());

        int startRow = request.getStartRow() == null ? 0 : request.getStartRow();
        int endRow = request.getEndRow() == null ? 20 : request.getEndRow();
        List<AiLog> list = aiLogMapper.selectByAdmin(adminId, startRow, endRow);
        return MineResp.success(new ListResponse(total, startRow + list.size() >= total, list));
    }

    /** 一次问答的完整轨迹 */
    public MineResp detail(Long id, Long adminId, Integer role) {
        AiLog log = aiLogMapper.selectById(id);
        if (log == null) return MineResp.error("记录不存在");
        boolean admin = role != null && role <= 1;
        if (!admin && (adminId == null || !adminId.equals(log.getAdminId()))) return MineResp.error("无权查看他人的记录");

        // actions / trace 原样给字符串，前端自己 parse：混进 JsonNode 会被 gson 序列化成 _children
        return MineResp.success(log);
    }

    private String handleTool(int step, String name, String argsText, List<Menus> menus,
                              List<AiAction> actions, Listener listener, ArrayNode trace) {
        String result;
        try {
            JsonNode args = argsText == null || argsText.trim().isEmpty()
                    ? json.createObjectNode() : json.readTree(argsText);
            if ("list_menus".equals(name)) {
                result = menusJson(menus);
            } else if ("navigate".equals(name)) {
                result = navigate(step, args, menus, actions, listener, trace);
            } else {
                result = "ERROR: 没有名为 " + name + " 的工具，可用工具: list_menus, navigate";
            }
        } catch (Exception e) {
            result = "ERROR: " + e.getMessage();
        }
        push(listener, trace, event("tool", step).put("tool", name).put("args", argsText).put("result", result));
        return result;
    }

    private String menusJson(List<Menus> menus) {
        ArrayNode array = json.createArrayNode();
        for (Menus menu : menus) {
            ObjectNode item = array.addObject();
            item.put("name", menu.getMenuName());
            item.put("path", menu.getPath());
        }
        return array.toString();
    }

    private String navigate(int step, JsonNode args, List<Menus> menus, List<AiAction> actions,
                            Listener listener, ArrayNode trace) {
        String path = args.path("path").asText("");
        Map<String, String> allowed = new LinkedHashMap<>();
        for (Menus menu : menus) {
            allowed.put(menu.getPath(), menu.getMenuName());
        }
        if (!allowed.containsKey(path)) {
            // 权限 + 存在性一起挡：不在当前用户菜单里的一律不给跳
            return "ERROR: 没有 path=" + path + " 的页面（或当前账号无权访问），可用 path: " + allowed.keySet();
        }
        String label = args.path("label").asText("");
        if (label.trim().isEmpty()) label = allowed.get(path);
        AiAction action = AiAction.navigate(path, label);
        actions.add(action);

        ObjectNode payload = event("action", step);
        payload.put("path", path);
        payload.put("label", label);
        push(listener, trace, payload);

        return "已跳转到 " + label + "(" + path + ")";
    }

    private void push(Listener listener, ArrayNode trace, ObjectNode payload) {
        trace.add(payload.deepCopy());
        if (listener == null) return;
        try {
            listener.onEvent(payload.path("type").asText(), payload);
        } catch (Exception e) {
            // 前端断开不该影响 agent 继续跑完
            logger.debug("推送事件失败: {}", e.getMessage());
        }
    }

    private ObjectNode event(String type, int step) {
        ObjectNode node = json.createObjectNode();
        node.put("type", type);
        node.put("step", step);
        return node;
    }

    private ObjectNode text(String role, String content) {
        ObjectNode message = json.createObjectNode();
        message.put("role", role);
        message.put("content", content);
        return message;
    }

    private String write(Object value) {
        try {
            return json.writeValueAsString(value);
        } catch (Exception e) {
            return "[]";
        }
    }

    private ArrayNode toolDefs() {
        ArrayNode defs = json.createArrayNode();

        ObjectNode listMenus = defs.addObject().put("type", "function");
        ObjectNode listFn = listMenus.putObject("function");
        listFn.put("name", "list_menus");
        listFn.put("description", "列出当前账号有权访问的菜单（name + path）");
        ObjectNode listParams = listFn.putObject("parameters");
        listParams.put("type", "object");
        listParams.putObject("properties");

        ObjectNode navigate = defs.addObject().put("type", "function");
        ObjectNode navFn = navigate.putObject("function");
        navFn.put("name", "navigate");
        navFn.put("description", "跳转到指定页面，path 必须来自 list_menus");
        ObjectNode navParams = navFn.putObject("parameters");
        navParams.put("type", "object");
        ObjectNode navProps = navParams.putObject("properties");
        navProps.putObject("path").put("type", "string").put("description", "目标页面 path");
        navProps.putObject("label").put("type", "string").put("description", "展示用的菜单名，可省略");
        navParams.putArray("required").add("path");

        return defs;
    }
}
