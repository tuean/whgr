package com.tuean.agent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** 用假客户端验证 agent loop：有 tool_calls 就执行并回填，直到模型不再调工具 */
class AgentLoopTest {

    @TempDir
    Path tmp;

    @Test
    void executesToolCallThenReturnsFinalAnswer() throws Exception {
        Path target = tmp.resolve("hello.txt");
        ScriptedClient llm = new ScriptedClient(
                toolCall("call_1", "write", "{\"path\":\"" + target + "\",\"content\":\"hi\\n\"}"),
                text("写好了"));

        Session session = new Session(tmp.resolve("sessions"));
        Agent agent = new Agent(llm, session, List.of(new WriteTool()), 5, tmp);

        String answer = agent.run("写个文件");

        assertEquals("写好了", answer);
        assertEquals("hi\n", Files.readString(target, StandardCharsets.UTF_8));
        // 会话里只有 4 条：user、assistant(tool_calls)、tool 结果、assistant(最终回答)，system 不进会话
        assertEquals(4, session.messages().size());
        assertEquals("tool", session.messages().get(2).path("role").asText());
        assertEquals("call_1", session.messages().get(2).path("tool_call_id").asText());
    }

    @Test
    void unknownToolBecomesErrorTextForTheModel() throws Exception {
        ScriptedClient llm = new ScriptedClient(
                toolCall("call_1", "nonexistent", "{}"),
                text("ok"));

        Agent agent = new Agent(llm, new Session(tmp.resolve("sessions")), List.of(new WriteTool()), 5, tmp);
        agent.run("试试不存在的工具");

        String toolResult = lastToolResult(llm.lastRequest);
        assertTrue(toolResult.startsWith("ERROR:"), toolResult);
    }

    private static String lastToolResult(List<JsonNode> messages) {
        return messages.get(messages.size() - 1).path("content").asText();
    }

    private static ObjectNode text(String content) {
        return Json.obj().put("role", "assistant").put("content", content);
    }

    private static ObjectNode toolCall(String id, String name, String argumentsJson) {
        ObjectNode message = Json.obj().put("role", "assistant");
        ObjectNode function = message.putArray("tool_calls").addObject()
                .put("id", id)
                .put("type", "function")
                .putObject("function");
        function.put("name", name);
        function.put("arguments", argumentsJson);
        return message;
    }

    /** 按顺序吐出预设消息，记录最后一次请求，方便断言回填内容 */
    private static final class ScriptedClient extends LlmClient {

        private final Deque<JsonNode> script = new ArrayDeque<>();
        private List<JsonNode> lastRequest = List.of();

        ScriptedClient(ObjectNode... messages) {
            super("http://localhost", "test-key", "test-model");
            script.addAll(List.of(messages));
        }

        @Override
        public JsonNode chat(List<JsonNode> messages, List<Tool> tools) {
            lastRequest = List.copyOf(messages);
            JsonNode next = script.poll();
            if (next == null) throw new IllegalStateException("脚本已用完");
            return next;
        }
    }
}
