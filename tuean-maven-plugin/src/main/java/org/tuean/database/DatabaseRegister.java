package org.tuean.database;

import org.tuean.consts.Env;
import org.tuean.entity.ConfigDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseRegister {

    private static Connection temp = null;

    public void registerClass(ConfigDb configDb) throws ClassNotFoundException {
        Class.forName(configDb.getDriver_class());
    }

    public Connection getConnection(ConfigDb configDb) throws SQLException {
        synchronized (this) {
            if (temp != null) return temp;
            temp = DriverManager.getConnection(configDb.getUrl(), configDb.getUser(), configDb.getPwd());
            return temp;
        }
    }

}