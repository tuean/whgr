package org.tuean.entity;

import java.util.List;
import java.util.Map;

public class XmlNode {

    private String tag;

    private Map<String, Object> tagAttrs;

    private List<XmlNode> nodes;

    private String content;


    public XmlNode() {
    }

    public XmlNode(String tag, Map<String, Object> tagAttrs, List<XmlNode> nodes, String content) {
        this.tag = tag;
        this.tagAttrs = tagAttrs;
        this.nodes = nodes;
        this.content = content;
    }

    @Override
    public String toString() {
        return "XmlNode{" +
                "tag='" + tag + '\'' +
                ", tagAttrs=" + tagAttrs +
                ", nodes=" + nodes +
                ", content='" + content + '\'' +
                '}';
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Map<String, Object> getTagAttrs() {
        return tagAttrs;
    }

    public void setTagAttrs(Map<String, Object> tagAttrs) {
        this.tagAttrs = tagAttrs;
    }

    public List<XmlNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<XmlNode> nodes) {
        this.nodes = nodes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
