package com.tuean.whgr.config;

import com.tuean.whgr.filter.mybatis.SqlLogIntercepts;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 读写分离数据库
 */
@Configuration
@MapperScan(basePackages = "com.tuean.whgr.dao",
        sqlSessionFactoryRef = "mainConfig"
)
public class DbReadWriteConfig implements EnvironmentAware {

    private static final String MAPPER_LOCATION = "classpath:mapper/main/*.xml";

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Autowired
    private SqlLogIntercepts sqlLogIntercepts;


    @Bean(name = "rwDB")
    public DataSource roDataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername(environment.getProperty("spring.datasource.main.username"));
        config.setPassword(environment.getProperty("spring.datasource.main.password"));
        config.setJdbcUrl(environment.getProperty("spring.datasource.main.url"));
        config.setDriverClassName(environment.getProperty("spring.datasource.main.driver-class-name"));
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
//        return DataSourceBuilder.create().build();
    }

    @Bean(name = "roDB")
    public DataSource rwDataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername(environment.getProperty("spring.datasource.docker.username"));
        config.setPassword(environment.getProperty("spring.datasource.docker.password"));
        config.setJdbcUrl(environment.getProperty("spring.datasource.docker.url"));
        config.setDriverClassName(environment.getProperty("spring.datasource.docker.driver-class-name"));
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean(name = "routingDB")
    public DataSource routingDataSource(@Qualifier("rwDB") DataSource rwDataSource, @Qualifier("roDB") DataSource roDataSource) {
        RoutableDataSource routableDataSource = new RoutableDataSource();
        Map<Object, Object> sourceMap = new HashMap<>();
        sourceMap.put("rwDB", rwDataSource);
        sourceMap.put("roDB", roDataSource);
        routableDataSource.setTargetDataSources(sourceMap);
        routableDataSource.setDefaultTargetDataSource(rwDataSource);
        return routableDataSource;
    }

    @Bean(name = "mainConfig")
    public SqlSessionFactory mainDbsource(@Qualifier("routingDB") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        bean.setPlugins(sqlLogIntercepts);
        return bean.getObject();
    }


}
