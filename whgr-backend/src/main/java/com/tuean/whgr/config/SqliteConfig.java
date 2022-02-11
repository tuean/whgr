package com.tuean.whgr.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.sqlite")
public class SqliteConfig {


    private String driverClassName;

    private String url;

}
