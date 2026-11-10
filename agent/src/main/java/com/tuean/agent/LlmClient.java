package com.tuean.agent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

/**
 * OpenAI 兼容的 /chat/completions 客户端（DeepSeek、OpenAI 或任何兼容网关都行）。
 * 非流式：一次拿到完整 assistant message，工具调用也就一次拿全。
 */
public class LlmClient {

    private static final int MAX_ATTEMPTS = 3;

    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(20))
            .build();

    private final String baseUrl;
    private final String apiKey;
    private final String model;

    public LlmClient(String baseUrl, String apiKey, String model) {
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.apiKey = apiKey;
        this.model = model;
    }

    public String model() {
        return model;
    }

    /** @return assistant message 原始 JSON */
    public JsonNode chat(List<JsonNode> messages, List<Tool> tools) {
        String payload = buildPayload(messages, tools);
        String lastError = null;

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                HttpResponse<String> resp = http.send(request(payload), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

                if (resp.statusCode() == 200) {
                    JsonNode message = Json.MAPPER.readTree(resp.body()).path("choices").path(0).path("message");
                    if (message.isMissingNode() || message.isNull()) {
                        throw new IllegalStateException("响应缺少 choices[0].message: " + shorten(resp.body(), 500));
                    }
                    return message;
                }

                if (resp.statusCode() == 429 || resp.statusCode() >= 500) {
                    lastError = "HTTP " + resp.statusCode() + ": " + shorten(resp.body(), 300);
                } else {
                    // 4xx（key 错、参数错）重试没用，直接失败
                    throw new IllegalStateException("HTTP " + resp.statusCode() + ": " + shorten(resp.body(), 800));
                }
            } catch (IOException e) {
                lastError = e.getClass().getSimpleName() + ": " + e.getMessage();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("模型调用被中断", e);
            }
            sleep(attempt);
        }
        throw new IllegalStateException("调用模型失败（已重试 " + MAX_ATTEMPTS + " 次）: " + lastError);
    }

    private HttpRequest request(String payload) {
        return HttpRequest.newBuilder(URI.create(baseUrl + "/chat/completions"))
                .timeout(Duration.ofMinutes(5))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(payload, StandardCharsets.UTF_8))
                .build();
    }

    private String buildPayload(List<JsonNode> messages, List<Tool> tools) {
        ObjectNode body = Json.obj();
        body.put("model", model);

        ArrayNode msgs = body.putArray("messages");
        messages.forEach(msgs::add);

        if (tools != null && !tools.isEmpty()) {
            ArrayNode defs = body.putArray("tools");
            for (Tool tool : tools) {
                ObjectNode def = defs.addObject().put("type", "function");
                ObjectNode fn = def.putObject("function");
                fn.put("name", tool.name());
                fn.put("description", tool.description());
                fn.set("parameters", tool.parameters());
            }
            body.put("tool_choice", "auto");
        }
        try {
            return Json.MAPPER.writeValueAsString(body);
        } catch (IOException e) {
            throw new IllegalStateException("请求序列化失败", e);
        }
    }

    private static void sleep(int attempt) {
        try {
            Thread.sleep(1000L * attempt);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    static String shorten(String text, int max) {
        if (text == null) return "";
        return text.length() <= max ? text : text.substring(0, max) + "…";
    }
}
