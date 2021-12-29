package org.tuean.util;

import org.jsoup.nodes.Attributes;

import java.util.HashMap;
import java.util.Map;

public class ParserXmlUtil {


    public static Map<String, Object> attributes2Map(Attributes attr) {
        Map<String, Object> tagAttrs = new HashMap<>();
        if (attr == null) return tagAttrs;
        for (String key : attr.dataset().keySet()) {
            tagAttrs.put(key, attr.get(key));
        }
        return tagAttrs;
    }



}
