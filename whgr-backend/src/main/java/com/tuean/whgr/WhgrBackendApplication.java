package com.tuean.whgr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.tuean.whgr.dao")
public class WhgrBackendApplication {

    private static Logger logger = LoggerFactory.getLogger(WhgrBackendApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WhgrBackendApplication.class, args);
    }

}
