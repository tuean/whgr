package org.tuean.entity;

public class ConfigDb {

    private String jar_path;

    private String driver_class;

    private String user;

    private String password;

    private String database;

    @Override
    public String toString() {
        return "ConfigDb{" +
                "jar_path='" + jar_path + '\'' +
                ", driver_class='" + driver_class + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", database='" + database + '\'' +
                '}';
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
