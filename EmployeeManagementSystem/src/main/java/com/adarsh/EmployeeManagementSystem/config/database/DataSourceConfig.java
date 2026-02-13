package com.adarsh.EmployeeManagementSystem.config.database;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    private final DatabaseProperties properties;

    public DataSourceConfig(DatabaseProperties properties) {
        this.properties = properties;
    }

    @Bean
    public DataSource  dataSource(){
        return DataSourceBuilder.create()
        .url(properties.getUrl())
        .username(properties.getUsername())
        .password(properties.getPassword())
        .build();
    }
}
