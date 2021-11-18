package org.tuean.entity;

import org.tuean.entity.define.JavaClass;

import java.util.Map;

public class MethodConfig {

    private String databaseName;

    private String tableName;

    private Map<JavaClass, MapperMethodConfig> config;



    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<JavaClass, MapperMethodConfig> getConfig() {
        return config;
    }

    public void setConfig(Map<JavaClass, MapperMethodConfig> config) {
        this.config = config;
    }
}
