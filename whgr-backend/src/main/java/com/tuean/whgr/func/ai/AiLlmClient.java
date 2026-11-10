package com.tuean.whgr.func.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * OpenAI 兼容的 /chat/completions 客户端（Java 8 HttpURLConnection 版）。
 * 只服务 AI 入口，所以没有流式、没有多轮会话管理。
 */
@Component
public class AiLlmClient {

    private static final Logger logger = LoggerFactory.getLogger(AiLlmClient.class);

    @Autowired
    private ObjectMapper json;

    @Value("${ai.base-url:https://api.deepseek.com}")
    private String baseUrl;

    @Value("${ai.model:deepseek-v4-flash}")
    private String model;

    @Value("${ai.api-key:}")
    private String apiKey;

    @Value("${ai.timeout-seconds:90}")
    private int timeoutSeconds;

    public boolean configured() {
        return apiKey != null && !apiKey.trim().isEmpty();
    }

    public String model() {
        return model;
    }

    /** @return assistant message 原始 JSON（含 content / tool_calls） */
    public JsonNode chat(List<ObjectNode> messages, ArrayNode tools) {
        String payload = buildPayload(messages, tools);
        String lastError = null;

        for (int attempt = 1; attempt <= 2; attempt++) {
            try {
                HttpResult result = post(baseUrl.replaceAll("/+$", "") + "/chat/completions", payload);

                if (result.status == 200) {
                    JsonNode message = json.readTree(result.body).path("choices").path(0).path("message");
                    if (message.isMissingNode() || message.isNull()) {
                        throw new IllegalStateException("模型响应缺少 choices[0].message");
                    }
                    return message;
                }
                if (result.status == 429 || result.status >= 500) {
                    lastError = "HTTP " + result.status + ": " + shorten(result.body);
                    logger.warn("ai 模型调用失败，准备重试: {}", lastError);
                } else {
                    // key 错、参数错这类重试没用
                    throw new IllegalStateException("模型调用失败 HTTP " + result.status + ": " + shorten(result.body));
                }
            } catch (IOException e) {
                lastError = e.getClass().getSimpleName() + ": " + e.getMessage();
                logger.warn("ai 模型调用异常，准备重试: {}", lastError);
            }

            try {
                Thread.sleep(1000L * attempt);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        throw new IllegalStateException("模型调用失败（已重试）: " + lastError);
    }

    private String buildPayload(List<ObjectNode> messages, ArrayNode tools) {
        ObjectNode root = json.createObjectNode();
        root.put("model", model);
        ArrayNode msgs = root.putArray("messages");
        for (ObjectNode message : messages) {
            msgs.add(message);
        }
        root.set("tools", tools);
        root.put("tool_choice", "auto");
        try {
            return json.writeValueAsString(root);
        } catch (IOException e) {
            throw new IllegalStateException("请求序列化失败", e);
        }
    }

    private HttpResult post(String url, String payload) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        try {
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(10_000);
            conn.setReadTimeout(Math.max(5, timeoutSeconds) * 1000);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);

            try (OutputStream out = conn.getOutputStream()) {
                out.write(payload.getBytes(StandardCharsets.UTF_8));
            }

            int status = conn.getResponseCode();
            InputStream in = status >= 400 ? conn.getErrorStream() : conn.getInputStream();
            return new HttpResult(status, readAll(in));
        } finally {
            conn.disconnect();
        }
    }

    private static String readAll(InputStream in) throws IOException {
        if (in == null) return "";
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] chunk = new byte[4096];
        int read;
        while ((read = in.read(chunk)) > 0) {
            buffer.write(chunk, 0, read);
        }
        return new String(buffer.toByteArray(), StandardCharsets.UTF_8);
    }

    private static String shorten(String text) {
        if (text == null) return "";
        return text.length() <= 300 ? text : text.substring(0, 300) + "…";
    }

    private static final class HttpResult {
        private final int status;
        private final String body;

        private HttpResult(int status, String body) {
            this.status = status;
            this.body = body;
        }
    }
}
