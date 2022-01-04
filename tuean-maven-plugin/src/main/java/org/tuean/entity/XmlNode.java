package org.tuean.entity;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlNode {

    private String tag;

    private Map<String, Object> tagAttrs;

    private List<XmlNode> nodes;

    private String content;



    private String id;


    public XmlNode() {
    }

    public XmlNode(String tag, Map<String, Object> tagAttrs, List<XmlNode> nodes, String content) {
        this.tag = tag;
        this.tagAttrs = tagAttrs;
        this.nodes = nodes;
        this.content = content;
    }

    public XmlNode(String tag, Map<String, Object> tagAttrs, List<XmlNode> nodes, String content, String id) {
        this.tag = tag;
        this.tagAttrs = tagAttrs;
        this.nodes = nodes;
        this.content = content;
        this.id = id;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        if (this.tagAttrs == null) this.tagAttrs = new HashMap<>();
        this.tagAttrs.put("id", id);
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
        if (StringUtils.isNotBlank(this.getId())) {
            if (this.tagAttrs == null) this.tagAttrs = new HashMap<>();
            this.tagAttrs.put("id", this.getId());
        }
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
