package com.tuean.agent;

import com.fasterxml.jackson.databind.JsonNode;

/** 工具入参取值，顺手做点校验（模型给的参数经常缺） */
final class Args {

    static String str(JsonNode args, String name) {
        String value = str(args, name, null);
        if (value == null || value.isBlank()) throw new IllegalArgumentException("参数 " + name + " 不能为空");
        return value;
    }

    static String str(JsonNode args, String name, String def) {
        JsonNode node = args.path(name);
        return node.isMissingNode() || node.isNull() ? def : node.asText();
    }

    static int intOr(JsonNode args, String name, int def) {
        JsonNode node = args.path(name);
        return node.isMissingNode() || node.isNull() ? def : node.asInt(def);
    }

    private Args() {
    }
}
