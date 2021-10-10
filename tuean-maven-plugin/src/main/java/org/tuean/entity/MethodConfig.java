package org.tuean.entity;

import java.util.Map;

public class MethodConfig {

    private String databaseName;

    private String tableName;

    private Map<DaoMethodConfig, MapperMethodConfig> config;



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

    public Map<DaoMethodConfig, MapperMethodConfig> getConfig() {
        return config;
    }

    public void setConfig(Map<DaoMethodConfig, MapperMethodConfig> config) {
        this.config = config;
    }
}
