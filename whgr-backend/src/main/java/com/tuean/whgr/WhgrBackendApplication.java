package com.tuean.whgr;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
//@MapperScan("com.tuean.whgr.dao")
@EnableScheduling
@EnableEncryptableProperties
@Configuration
public class WhgrBackendApplication {

    public static void main(String[] args) {
//        SpringApplication.run(WhgrBackendApplication.class, args);
        new SpringApplicationBuilder()
                .environment(new StandardEncryptableEnvironment())
                .sources(WhgrBackendApplication.class)
                .run(args);
    }


}
