package com.tuean.agent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** 带行号读文件，超长自动截断（对齐 pi 的 read） */
public class ReadTool implements Tool {

    private static final int MAX_LINES = 2000;
    private static final int MAX_CHARS = 50_000;
    private static final long MAX_FILE_BYTES = 5L * 1024 * 1024;
    private static final ObjectNode PARAMS = params();

    @Override
    public String name() {
        return "read";
    }

    @Override
    public String description() {
        return "Read a text file with line numbers. Use offset/limit for large files. Always read a file before editing it.";
    }

    @Override
    public JsonNode parameters() {
        return PARAMS;
    }

    @Override
    public String execute(JsonNode args) throws Exception {
        Path path = Path.of(Args.str(args, "path"));
        if (!Files.isRegularFile(path)) throw new IllegalArgumentException("文件不存在: " + path);

        long size = Files.size(path);
        if (size > MAX_FILE_BYTES) throw new IllegalArgumentException("文件过大(" + size + " 字节)，请用 bash 分段处理");

        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        int offset = Math.max(1, Args.intOr(args, "offset", 1));
        int limit = Math.max(1, Args.intOr(args, "limit", MAX_LINES));

        int from = Math.min(offset - 1, lines.size());
        int to = Math.min(lines.size(), from + limit);

        StringBuilder sb = new StringBuilder();
        for (int i = from; i < to && sb.length() < MAX_CHARS; i++) {
            sb.append(i + 1).append('→').append(lines.get(i)).append('\n');
        }
        if (to < lines.size()) {
            sb.append("… 还有 ").append(lines.size() - to).append(" 行未显示，用 offset=").append(to + 1).append(" 继续读\n");
        }
        return sb.length() == 0 ? "(空文件)" : sb.toString();
    }

    private static ObjectNode params() {
        ObjectNode p = Json.obj().put("type", "object");
        ObjectNode props = p.putObject("properties");
        props.putObject("path").put("type", "string").put("description", "文件路径，相对当前目录或绝对路径");
        props.putObject("offset").put("type", "integer").put("description", "起始行号，1 起，默认 1");
        props.putObject("limit").put("type", "integer").put("description", "最多读多少行，默认 2000");
        p.putArray("required").add("path");
        return p;
    }
}
