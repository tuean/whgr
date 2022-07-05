package com.tuean.whgr.config;

import com.tuean.whgr.pool.RequestThreadPoolTaskExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

/**
 * not support for multipartFile
 */
//@Configuration
//@EnableAsync
public class AsyncConfig implements AsyncConfigurer {


    @Bean
    public Executor reqExecutor() {
        return RequestThreadPoolTaskExecutor.getExecutor();
    }


}
