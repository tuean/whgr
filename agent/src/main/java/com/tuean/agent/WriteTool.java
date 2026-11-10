package com.tuean.agent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/** 整文件写入（覆盖/新建），改局部请用 edit */
public class WriteTool implements Tool {

    private static final ObjectNode PARAMS = params();

    @Override
    public String name() {
        return "write";
    }

    @Override
    public String description() {
        return "Create or overwrite a whole file with the given content. Parent directories are created. For changing part of an existing file prefer edit.";
    }

    @Override
    public JsonNode parameters() {
        return PARAMS;
    }

    @Override
    public String execute(JsonNode args) throws Exception {
        Path path = Path.of(Args.str(args, "path"));
        String content = Args.str(args, "content", "");

        Path parent = path.toAbsolutePath().getParent();
        if (parent != null) Files.createDirectories(parent);
        Files.writeString(path, content, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        return "已写入 " + path + "（" + content.lines().count() + " 行，" + content.length() + " 字符）";
    }

    private static ObjectNode params() {
        ObjectNode p = Json.obj().put("type", "object");
        ObjectNode props = p.putObject("properties");
        props.putObject("path").put("type", "string").put("description", "文件路径");
        props.putObject("content").put("type", "string").put("description", "完整文件内容");
        p.putArray("required").add("path").add("content");
        return p;
    }
}
