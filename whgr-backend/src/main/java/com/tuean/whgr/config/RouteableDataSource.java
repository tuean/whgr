package com.tuean.whgr.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RouteableDataSource extends AbstractRoutingDataSource {

    private static Map<String, DataSource> map = new ConcurrentHashMap<>();

    private static ThreadLocal<String> dbSelector = new ThreadLocal<>();

    private static String DEFAULT_DB = null;

    public static String get() {
        return dbSelector.get() == null ? DEFAULT_DB : dbSelector.get();
    }

    public static void set(String db) {
        dbSelector.set(db);
    }

    public static void set(String key, DataSource s) {
        map.put(key, s);
    }

    public static DataSource get(String key) {
        DataSource source = map.get(key);
        Assert.notNull(source, "dataSource not found");
        return source;
    }


    @Override
    protected Object determineCurrentLookupKey() {
        return dbSelector.get();
    }

//    @Override
//    protected DataSource determineTargetDataSource() {
//
//    }

}
