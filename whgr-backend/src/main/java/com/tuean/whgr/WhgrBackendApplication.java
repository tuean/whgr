package com.tuean.whgr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.tuean.whgr.dao")
@EnableScheduling
public class WhgrBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhgrBackendApplication.class, args);
    }

}
