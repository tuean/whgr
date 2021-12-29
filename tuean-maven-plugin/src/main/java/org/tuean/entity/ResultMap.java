package org.tuean.entity;

@Deprecated
public class ResultMap {

    private String id;

    private String type;

    private String extendsType;

    private Boolean autoMapping;


    public ResultMap(String id, String type, String extendsType, Boolean autoMapping) {
        this.id = id;
        this.type = type;
        this.extendsType = extendsType;
        this.autoMapping = autoMapping;
    }

    @Override
    public String toString() {
        return "ResultMap{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", extendsType='" + extendsType + '\'' +
                ", autoMapping=" + autoMapping +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExtendsType() {
        return extendsType;
    }

    public void setExtendsType(String extendsType) {
        this.extendsType = extendsType;
    }

    public Boolean getAutoMapping() {
        return autoMapping;
    }

    public void setAutoMapping(Boolean autoMapping) {
        this.autoMapping = autoMapping;
    }
}
