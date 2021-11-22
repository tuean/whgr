package org.tuean.entity;

public class ConfigDb {

    private String jar_path;

    private String driver_class;

    private String user;

    private String pwd;

    private String database;

    private String url;

    public ConfigDb() {
    }

    public ConfigDb(String jar_path, String driver_class, String user, String pwd, String database, String url) {
        this.jar_path = jar_path;
        this.driver_class = driver_class;
        this.user = user;
        this.pwd = pwd;
        this.database = database;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJar_path() {
        return jar_path;
    }

    public void setJar_path(String jar_path) {
        this.jar_path = jar_path;
    }

    public String getDriver_class() {
        return driver_class;
    }

    public void setDriver_class(String driver_class) {
        this.driver_class = driver_class;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
