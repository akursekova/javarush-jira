package com.javarush.jira.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    private static final String PROPERTY_DRIVER = "spring.datasource.driverClassName";
    private static final String PROPERTY_URL = "spring.datasource.url";
    private static final String PROPERTY_USERNAME = "spring.datasource.username";
    private static final String PROPERTY_PASSWORD = "spring.datasource.password";

    @Autowired
    Environment env;

    @Bean(name = "prodDataSource")
    @Profile("prod")
    DataSource dataSource() {
        return createDataSource();
    }

    @Bean("testDataSource")
    @Profile("test")
    DataSource dataTestSource() {
        return createDataSource();
    }

    private DataSource createDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty(PROPERTY_DRIVER));
        dataSource.setUrl(env.getProperty(PROPERTY_URL));
        dataSource.setUsername(env.getProperty(PROPERTY_USERNAME));
        dataSource.setPassword(env.getProperty(PROPERTY_PASSWORD));
        return dataSource;
    }

}
