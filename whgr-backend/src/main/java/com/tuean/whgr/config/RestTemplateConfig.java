package com.tuean.whgr.config;

import com.tuean.whgr.http.RestTemplateMdcRequestInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@Component
public class RestTemplateConfig {

    @Bean(name = "mineRestTemplate")
    public RestTemplate init() {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .additionalMessageConverters(new GsonHttpMessageConverter())
                .additionalMessageConverters(new StringHttpMessageConverter())
                .additionalInterceptors(new RestTemplateMdcRequestInterceptor())
                .setConnectTimeout(Duration.ofMillis(1000))
                .setReadTimeout(Duration.ofMillis(1000))
                .build();
        return restTemplate;
    }

}
