package com.tuean.whgr;

import com.timgroup.statsd.StatsDClient;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.tuean.autoconfiguration.prometheus.EnablePrometheusAutoConfiguration;
import org.tuean.autoconfiguration.tdengine.CachedStatsdClient;
import org.tuean.autoconfiguration.tdengine.EnableTdengineAutoConfiguration;

import javax.annotation.PostConstruct;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        RestTemplateAutoConfiguration.class
})
//@MapperScan("com.tuean.whgr.dao")
@EnableScheduling
@EnableEncryptableProperties
@Configuration
@EnablePrometheusAutoConfiguration
@EnableTdengineAutoConfiguration
public class WhgrBackendApplication {

    private static Logger logger = LoggerFactory.getLogger(WhgrBackendApplication.class);

    public static void main(String[] args) {
//        SpringApplication.run(WhgrBackendApplication.class, args);
        new SpringApplicationBuilder()
                .environment(new StandardEncryptableEnvironment())
                .sources(WhgrBackendApplication.class)
                .run(args);
    }


}
