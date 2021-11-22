package org.tuean.database;

import org.tuean.consts.Env;
import org.tuean.entity.ConfigDb;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseRegister {

    private static Connection temp = null;
    private static boolean loadFlag = false;
    private static Driver driver;

    private static class Holder {
        private static final DatabaseRegister register = new DatabaseRegister();
    }

    public static Class registerClass(ConfigDb configDb) throws ClassNotFoundException, MalformedURLException {
        String jarFile = configDb.getJar_path();
        URL[] urls = new URL[1];
        urls[0] = new File(jarFile).toURI().toURL();
//        URLClassLoader classLoader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());
        ClassLoader classLoader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());

        return Class.forName(configDb.getDriver_class(), true,  classLoader);
    }

    public static Connection getConnection(ConfigDb configDb) throws SQLException, ClassNotFoundException {
        if (!loadFlag) {
            try {
                Class clazz = registerClass(configDb);
                driver = (Driver) clazz.getConstructor().newInstance();
            } catch (MalformedURLException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (temp != null && !temp.isClosed()) return temp;
        Properties props = new Properties();
        props.setProperty("user", configDb.getUser());
        props.setProperty("password", configDb.getPwd());
        temp = driver.connect(configDb.getUrl(), props);
        return temp;
    }

}
