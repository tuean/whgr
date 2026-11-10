package com.tuean.agent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 会话历史 + JSONL 落盘（对应 pi 的 session 文件）。
 * 历史里存的是模型返回的原始消息 JSON，回传时原样带上，
 * 这样 DeepSeek 的 reasoning_content 之类的额外字段不会丢。
 */
public class Session {

    private final List<JsonNode> messages = new ArrayList<>();
    private final ObjectMapper mapper = Json.MAPPER;
    private final Path file;

    public Session(Path dir) {
        try {
            Files.createDirectories(dir);
            this.file = dir.resolve("session-" + System.currentTimeMillis() + ".jsonl");
        } catch (IOException e) {
            throw new UncheckedIOException("创建会话目录失败: " + dir, e);
        }
    }

    public List<JsonNode> messages() {
        return Collections.unmodifiableList(messages);
    }

    public Path file() {
        return file;
    }

    public void addUser(String text) {
        add(Json.obj().put("role", "user").put("content", text));
    }

    /** 模型返回的 assistant message（含 content / tool_calls / reasoning_content） */
    public void addAssistant(JsonNode message) {
        add(message.deepCopy());
    }

    public void addToolResult(String toolCallId, String content) {
        add(Json.obj()
                .put("role", "tool")
                .put("tool_call_id", toolCallId)
                .put("content", content));
    }

    private void add(JsonNode message) {
        messages.add(message);
        try {
            Files.writeString(file, mapper.writeValueAsString(message) + "\n", StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            // 落盘失败不影响对话，只提示
            System.err.println("[session] 写入失败: " + e.getMessage());
        }
    }
}
