package org.tuean.util;

import org.apache.commons.collections.CollectionUtils;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.tuean.entity.XmlNode;

import java.util.*;

public class ParserXmlUtil {


    public static Map<String, Object> attributes2Map(Attributes attr) {
        Map<String, Object> tagAttrs = new HashMap<>();
        if (attr == null) return tagAttrs;
        for (Attribute a : attr.asList()) {
            tagAttrs.put(a.getKey(), a.getValue());
        }
        return tagAttrs;
    }


    public static XmlNode parse(Element element) {
        if (element == null) return null;
        String tagName = element.tagName();
        Map<String, Object> tagAttrs = attributes2Map(element.attributes());
        String id = obj2Str(tagAttrs.get("id"));
        List<XmlNode> child = new LinkedList<>();

        if (!CollectionUtils.isEmpty(element.children())) {
            for (Element c : element.children()) {
                XmlNode node = ParserXmlUtil.parse(c);
                if (node != null) child.add(node);
            }
        }

        String text = element.text();
        return new XmlNode(tagName, tagAttrs, child, text, id);
    }


    private static String obj2Str(Object o) {
        return o == null ? "" : String.valueOf(o);
    }


}
