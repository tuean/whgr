package org.tuean.entity;

import java.util.List;
import java.util.Map;

public class CodeGenerateConfig {

    private ConfigDb db;

    private ConfigMapper mapper;

    private Map<String, ConfigGenerator> generator;

    @Override
    public String toString() {
        return "CodeGenerateConfig{" +
                "db=" + db +
                ", mapper=" + mapper +
                ", generator=" + generator +
                '}';
    }

    public ConfigDb getDb() {
        return db;
    }

    public void setDb(ConfigDb db) {
        this.db = db;
    }

    public ConfigMapper getMapper() {
        return mapper;
    }

    public void setMapper(ConfigMapper mapper) {
        this.mapper = mapper;
    }

    public Map<String, ConfigGenerator> getGenerator() {
        return generator;
    }

    public void setGenerator(Map<String, ConfigGenerator> generator) {
        this.generator = generator;
    }
}
