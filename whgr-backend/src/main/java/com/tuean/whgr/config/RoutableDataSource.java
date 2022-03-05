package com.tuean.whgr.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RoutableDataSource extends AbstractRoutingDataSource {

    private static Map<String, DataSource> map = new ConcurrentHashMap<>();

    private static ThreadLocal<String> dbSelector = new ThreadLocal<>();

    private static String DEFAULT_DB = null;

    public static String get() {
        return dbSelector.get() == null ? DEFAULT_DB : dbSelector.get();
    }

    public static void set(String db) {
        log.debug("setting db: {}", db);
        dbSelector.set(db);
    }

    public static void set(String key, DataSource s) {
        map.put(key, s);
    }

    public static DataSource get(String key) {
        DataSource source = map.get(key);
        Assert.notNull(source, "dataSource can not be null");
        return source;
    }


    @Override
    protected Object determineCurrentLookupKey() {
        return dbSelector.get();
    }


}
