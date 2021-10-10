package org.tuean.entity;

import org.tuean.consts.SqlType;

import java.util.Map;

public class MapperMethodConfig {

    private boolean exist;

    private Map<String, String> dbColumnMap;

    private String sqlId;

    private SqlType sqlType;

    private String sql;

    private String resultType;

    private String parameterType;


    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public Map<String, String> getDbColumnMap() {
        return dbColumnMap;
    }

    public void setDbColumnMap(Map<String, String> dbColumnMap) {
        this.dbColumnMap = dbColumnMap;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public SqlType getSqlType() {
        return sqlType;
    }

    public void setSqlType(SqlType sqlType) {
        this.sqlType = sqlType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }
}
