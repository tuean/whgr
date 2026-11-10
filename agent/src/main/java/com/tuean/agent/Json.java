package com.tuean.agent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/** Jackson 的小工具，省得每处都 new ObjectMapper() */
final class Json {

    static final ObjectMapper MAPPER = new ObjectMapper();

    static ObjectNode obj() {
        return JsonNodeFactory.instance.objectNode();
    }

    private Json() {
    }
}
