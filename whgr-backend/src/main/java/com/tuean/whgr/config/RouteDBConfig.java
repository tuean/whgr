package com.tuean.whgr.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static com.tuean.whgr.config.Const.DB_MAIN;

@Configuration
public class RouteDBConfig {

    @Qualifier("mainDB")
    @Resource
    private DataSource mainDB;

    @Bean
    public DataSource allDataSource() {
        Map<Object, Object> all = new HashMap<>();
        all.put(DB_MAIN, mainDB);
        RouteableDataSource routeableDataSource = new RouteableDataSource();
        routeableDataSource.setTargetDataSources(all);
        return routeableDataSource;
    }

}
