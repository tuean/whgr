package com.tuean.whgr.func.ai;

/** 前端要执行的动作，目前只有跳转一种，留着 type 方便以后加（打开弹窗、填表单…） */
public class AiAction {

    public static final String NAVIGATE = "navigate";

    private String type;

    private String path;

    private String label;

    public AiAction() {
    }

    public AiAction(String type, String path, String label) {
        this.type = type;
        this.path = path;
        this.label = label;
    }

    public static AiAction navigate(String path, String label) {
        return new AiAction(NAVIGATE, path, label);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
