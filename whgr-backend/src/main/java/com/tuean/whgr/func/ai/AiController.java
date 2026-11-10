package com.tuean.whgr.func.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tuean.whgr.entity.BaseListRequest;
import com.tuean.whgr.entity.MineResp;
import com.tuean.whgr.entity.db.AdminAccount;
import com.tuean.whgr.helper.AdminAccountHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@RestController
public class AiController {

    private static final Logger logger = LoggerFactory.getLogger(AiController.class);

    /** agent 跑在独立线程里，请求线程立刻返回，事件通过 SSE 推回去 */
    private static final ExecutorService POOL = Executors.newFixedThreadPool(4, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "ai-chat");
            thread.setDaemon(true);
            return thread;
        }
    });

    @Autowired
    private AiService aiService;

    @Autowired
    private ObjectMapper json;

    /**
     * AI 入口（SSE）：请求体 {message}，逐条推送
     * {type:"thinking"|"tool"|"action"|"answer"|"done"|"error", step, ...}
     */
    @RequestMapping(value = "/ai/chat", method = RequestMethod.POST)
    public SseEmitter chat(@RequestBody Map<String, String> body) {
        AdminAccount me = AdminAccountHelper.get();
        final Long adminId = me == null ? null : me.getId();
        final Integer role = me == null ? null : me.getRole();
        final String message = body == null ? null : body.get("message");

        final SseEmitter emitter = new SseEmitter(180_000L);
        POOL.execute(() -> {
            try {
                Long logId = aiService.chat(message, adminId, role, (type, payload) -> send(emitter, payload));
                ObjectNode done = json.createObjectNode().put("type", "done");
                if (logId != null) done.put("logId", logId);
                send(emitter, done);
                emitter.complete();
            } catch (Exception e) {
                logger.warn("ai chat 失败: {}", e.getMessage());
                send(emitter, json.createObjectNode().put("type", "error").put("message", String.valueOf(e.getMessage())));
                emitter.complete();
            }
        });
        return emitter;
    }

    /** 我的历史问答列表（回溯入口），入参 {pageNum,pageSize} */
    @RequestMapping(value = "/ai/history", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp history(@RequestBody BaseListRequest request) {
        AdminAccount me = AdminAccountHelper.get();
        return aiService.history(request, me == null ? null : me.getId());
    }

    /** 一次问答的完整轨迹（思考 + 工具调用 + 跳转 + 回答） */
    @RequestMapping(value = "/ai/history/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp historyDetail(@PathVariable("id") Long id) {
        AdminAccount me = AdminAccountHelper.get();
        return aiService.detail(id, me == null ? null : me.getId(), me == null ? null : me.getRole());
    }

    private void send(SseEmitter emitter, ObjectNode payload) {
        try {
            // 直接发 JSON 字符串：交给 converter 序列化 ObjectNode 会被 gson 写成 _children 结构
            emitter.send(payload.toString(), MediaType.TEXT_PLAIN);
        } catch (Exception e) {
            // 客户端断开就丢掉，agent 继续跑完并落库
            logger.debug("sse 发送失败: {}", e.getMessage());
        }
    }

}
