package org.tuean.entity;

public class ConfigGenerator {

    private String entity;

    private String dao;

    private String xml;

    @Override
    public String toString() {
        return "ConfigGenerator{" +
                "entity='" + entity + '\'' +
                ", dao='" + dao + '\'' +
                ", xml='" + xml + '\'' +
                '}';
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getDao() {
        return dao;
    }

    public void setDao(String dao) {
        this.dao = dao;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }
}
