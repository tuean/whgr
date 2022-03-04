package com.tuean.whgr.config;

import com.tuean.whgr.filter.mybatis.SqlLogIntercepts;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.tuean.whgr.dao",
        sqlSessionFactoryRef = "mainConfig"
)
public class MainDbConfig implements EnvironmentAware {

    private static final String MAPPER_LOCATION = "classpath:mapper/main/*.xml";

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Autowired
    private SqlLogIntercepts sqlLogIntercepts;


    @Bean(name = "mainDB")
//    @ConfigurationProperties(prefix = "spring.datasource.main")
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername(environment.getProperty("spring.datasource.main.username"));
        config.setPassword(environment.getProperty("spring.datasource.main.password"));
        config.setJdbcUrl(environment.getProperty("spring.datasource.main.url"));
        config.setDriverClassName(environment.getProperty("spring.datasource.main.driver-class-name"));
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
//        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mainConfig")
    public SqlSessionFactory mainDbsource(@Qualifier("mainDB") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        bean.setPlugins(sqlLogIntercepts);
        return bean.getObject();
    }


}
