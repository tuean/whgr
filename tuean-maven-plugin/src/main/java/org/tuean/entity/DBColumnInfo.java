package org.tuean.entity;

public class DBColumnInfo {

    private String name;

    private String type;

    private String key;

    public DBColumnInfo(String name, String type, String key) {
        this.name = name;
        this.type = type;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
