package com.tuean.agent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 精确文本替换（对齐 pi 的 edit 语义）：
 * 每个 oldText 必须唯一命中原文、多处 edits 不许重叠、全部按原文匹配后再一次性落盘。
 */
public class EditTool implements Tool {

    private static final ObjectNode PARAMS = params();
    private static final int PREVIEW = 120;

    @Override
    public String name() {
        return "edit";
    }

    @Override
    public String description() {
        return "Replace exact text in a file. Each edits[].oldText must match the ORIGINAL file exactly once and must not overlap other edits; newText=\"\" deletes the matched text. Multiple independent edits go in one call.";
    }

    @Override
    public JsonNode parameters() {
        return PARAMS;
    }

    @Override
    public String execute(JsonNode args) throws Exception {
        Path path = Path.of(Args.str(args, "path"));
        if (!Files.isRegularFile(path)) throw new IllegalArgumentException("文件不存在: " + path);

        String original = Files.readString(path, StandardCharsets.UTF_8);
        JsonNode edits = args.path("edits");
        if (!edits.isArray() || edits.isEmpty()) throw new IllegalArgumentException("edits 必须是非空数组");

        record Replacement(int start, int end, String text) {
        }

        List<Replacement> replacements = new ArrayList<>();
        for (JsonNode edit : edits) {
            String oldText = Args.str(edit, "oldText");
            String newText = Args.str(edit, "newText", "");

            int first = original.indexOf(oldText);
            if (first < 0) throw new IllegalArgumentException("oldText 未在文件中匹配到: " + preview(oldText));
            if (original.indexOf(oldText, first + 1) >= 0) {
                throw new IllegalArgumentException("oldText 匹配到多处，请给出更长的上下文使其唯一: " + preview(oldText));
            }
            replacements.add(new Replacement(first, first + oldText.length(), newText));
        }

        replacements.sort(Comparator.comparingInt(Replacement::start));
        for (int i = 1; i < replacements.size(); i++) {
            if (replacements.get(i).start() < replacements.get(i - 1).end()) {
                throw new IllegalArgumentException("edits 之间存在重叠区域，请拆开或合并成一处");
            }
        }

        StringBuilder sb = new StringBuilder(original);
        for (int i = replacements.size() - 1; i >= 0; i--) {
            Replacement r = replacements.get(i);
            sb.replace(r.start(), r.end(), r.text());
        }
        String updated = sb.toString();
        if (updated.equals(original)) return "内容无变化: " + path;

        Files.writeString(path, updated, StandardCharsets.UTF_8);
        return "已修改 " + path + "（" + replacements.size() + " 处）";
    }

    private static String preview(String text) {
        String flat = text.replace("\n", "\\n");
        return flat.length() <= PREVIEW ? flat : flat.substring(0, PREVIEW) + "…";
    }

    private static ObjectNode params() {
        ObjectNode p = Json.obj().put("type", "object");
        ObjectNode props = p.putObject("properties");
        props.putObject("path").put("type", "string").put("description", "要修改的文件路径");

        ObjectNode edits = props.putObject("edits");
        edits.put("type", "array").put("description", "一处或多处替换，按原文匹配");
        ObjectNode item = edits.putObject("items");
        item.put("type", "object");
        ObjectNode itemProps = item.putObject("properties");
        itemProps.putObject("oldText").put("type", "string").put("description", "原文中唯一存在的片段");
        itemProps.putObject("newText").put("type", "string").put("description", "替换后的内容，空字符串表示删除");
        item.putArray("required").add("oldText").add("newText");

        p.putArray("required").add("path").add("edits");
        return p;
    }
}
