package com.tuean.whgr.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

//@Configuration
//@MapperScan(basePackages = "com.tuean.whgr.dao",
//        sqlSessionFactoryRef = "mainConfig"
//)
public class MainDbConfig implements EnvironmentAware {

    private static final String MAPPER_LOCATION = "classpath:/mapper/main/*.xml";

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Bean(name = "mainDB")
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername(environment.getProperty("spring.datasource.username"));
        config.setPassword(environment.getProperty("spring.datasource.password"));
        config.setJdbcUrl(environment.getProperty("spring.datasource.url"));
        config.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean(name = "mainConfig")
    public SqlSessionFactory mainDbsource(@Qualifier("mainDB") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResource(MAPPER_LOCATION));
        return bean.getObject();
    }


}
