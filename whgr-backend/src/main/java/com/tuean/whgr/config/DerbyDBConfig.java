package com.tuean.whgr.config;

import com.tuean.whgr.filter.mybatis.SqlLogIntercepts;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.sqlite")
public class DerbyDBConfig {

    @Autowired
    private SqlLogIntercepts sqlLogIntercepts;


    private String driverClassName;

    private String url;

}
