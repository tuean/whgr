package com.tuean.agent;

import com.fasterxml.jackson.databind.JsonNode;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * agent 主循环（对齐 pi 的 agent loop）：
 * 组装请求（system + 历史 + 工具）→ 模型返回 assistant 消息 → 有 tool_calls 就逐个执行并把结果回填 → 再请求，
 * 直到模型不再调工具（或到达 maxSteps）。
 */
public class Agent {

    private final LlmClient llm;
    private final Session session;
    private final List<Tool> tools;
    private final int maxSteps;
    private final String systemPrompt;

    public Agent(LlmClient llm, Session session, List<Tool> tools, int maxSteps, Path cwd) {
        this.llm = llm;
        this.session = session;
        this.tools = tools;
        this.maxSteps = maxSteps;
        this.systemPrompt = buildSystemPrompt(cwd);
    }

    public Session session() {
        return session;
    }

    /** 跑一轮对话，返回模型最后的文字回答 */
    public String run(String userInput) {
        session.addUser(userInput);

        for (int step = 1; step <= maxSteps; step++) {
            JsonNode message = llm.chat(messagesForModel(), tools);
            session.addAssistant(message);

            JsonNode toolCalls = message.path("tool_calls");
            if (!toolCalls.isArray() || toolCalls.isEmpty()) {
                return message.path("content").asText("");
            }

            for (JsonNode call : toolCalls) {
                String name = call.path("function").path("name").asText();
                String rawArgs = call.path("function").path("arguments").asText("{}");

                System.out.printf("[step %d] %s %s%n", step, name, oneLine(rawArgs, 160));
                String output = execute(name, rawArgs);
                System.out.printf("[step %d]   ← %s%n", step, oneLine(output, 160));

                session.addToolResult(call.path("id").asText(), output);
            }
        }
        return "[已连续调用 " + maxSteps + " 步仍未收尾，可加大 AGENT_MAX_STEPS 或把目标拆小]";
    }

    private List<JsonNode> messagesForModel() {
        List<JsonNode> messages = new ArrayList<>();
        messages.add(Json.obj().put("role", "system").put("content", systemPrompt));
        messages.addAll(session.messages());
        return messages;
    }

    /** 工具异常不往上抛：转成 ERROR 文本回给模型，让它自己改参数重试 */
    private String execute(String name, String rawArgs) {
        Tool tool = tools.stream().filter(t -> t.name().equals(name)).findFirst().orElse(null);
        if (tool == null) {
            return "ERROR: 没有名为 " + name + " 的工具，可用工具: "
                    + tools.stream().map(Tool::name).toList();
        }
        try {
            JsonNode args = rawArgs == null || rawArgs.isBlank() ? Json.obj() : Json.MAPPER.readTree(rawArgs);
            return tool.execute(args);
        } catch (Exception e) {
            return "ERROR: " + e.getClass().getSimpleName() + ": " + e.getMessage();
        }
    }

    private static String oneLine(String text, int max) {
        if (text == null) return "";
        String flat = text.replace('\n', ' ').strip();
        return flat.length() <= max ? flat : flat.substring(0, max) + "…";
    }

    private static String buildSystemPrompt(Path cwd) {
        return """
                你是运行在 %s 目录下的编码 agent，通过工具读写文件、执行命令来完成任务。

                工具：read（读文件，带行号）、write（整文件写入）、edit（精确文本替换）、bash（跑 shell 命令）。
                规则：
                - 改文件前先 read，不要凭记忆猜文件内容。
                - edit 的 oldText 必须与文件内容逐字符一致且在文件中唯一；互不相关的多处改动放在同一个 edit 调用里。
                - 改动尽量小且完整，保持代码原有风格；不要顺手重构没被要求的东西。
                - bash 的输出（退出码、报错）要真看一眼再下结论，不要假设成功。
                - 工具返回 ERROR 时修正参数重试，不要重复同样的调用。
                - 用用户的语言回答；完成后用一两句话说明改了什么。
                """.formatted(cwd);
    }
}
