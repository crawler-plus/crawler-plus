package com.crawler.components;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(prefix = "slave", name = "enable", havingValue = "true")
@MapperScan(basePackages = HikariDataSourceConfiguration.PACKAGE, sqlSessionFactoryRef = "slaveSqlSessionFactory")
public class HikariDataSourceConfiguration {

    // 精确到 slave mybatis 接口目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.crawler.dao";

    // 精确到 slave mabatis xml目录，以便跟其他数据源隔离
    private static final String MAPPER_LOCATION = "classpath:sqlmapper/*.xml";

    @Value("${slave.datasource.url}")
    private String url;

    @Value("${slave.datasource.username}")
    private String username;

    @Value("${slave.datasource.password}")
    private String password;

    @Value("${slave.datasource.driverClassName}")
    private String driverClassName;

    @Bean(name = "slaveDataSource")
    public DataSource slaveDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        return hikariDataSource;
    }

    @Bean(name = "slaveTransactionManager")
    public DataSourceTransactionManager slaveTransactionManager() {
        return new DataSourceTransactionManager(slaveDataSource());
    }

    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slaveDataSource") DataSource slaveDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(slaveDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(HikariDataSourceConfiguration.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
