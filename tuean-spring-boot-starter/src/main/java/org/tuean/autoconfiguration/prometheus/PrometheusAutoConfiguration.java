package org.tuean.autoconfiguration.prometheus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrometheusAutoConfiguration {

    private static Logger logger = LoggerFactory.getLogger(PrometheusAutoConfiguration.class);

    @Bean(name = "PrometheusFilter")
    public RegistrationBean metricsServlet() {
        logger.info("Initializing Prometheus metrics servlet");
        FilterRegistrationBean<PrometheusFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new PrometheusFilter());
        bean.addUrlPatterns("/*");
        bean.setName("PrometheusFilter");
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public PrometheusComponent component() {
        return new PrometheusComponent();
    }

}

