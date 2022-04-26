package org.tuean.autoconfiguration.tdengine;

import com.timgroup.statsd.NonBlockingStatsDClientBuilder;
import com.timgroup.statsd.StatsDClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatsdAutoConfiguration {

    @Value("${statsd.host:localhost}")
    private String host;

    @Value("${statsd.port:8125}")
    private int port;

    @Value("${spring.application.name:application}")
    private String applicationName;

    @Bean(name = "statsdClient")
    @ConditionalOnMissingBean(CachedStatsdClient.class)
    public CachedStatsdClient statsDClient() {
        CachedStatsdClient client = new CachedStatsdClient(host, port, applicationName);
        return client;
    }

    @Bean(name = "apiCounter")
    @ConditionalOnMissingBean(name = "apiCounter")
    public StatsdApiCountFilter apiCounter() {
        return new StatsdApiCountFilter();
    }

}
