package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Autowired
    private Environment env;

    /**
     * MySQL database initializer for production profile
     */
    @Bean
    @Profile("prod")
    public DataSourceInitializer mysqlDataSourceInitializer(DataSource dataSource) {
        // Only initialize if explicitly enabled in properties
        String initDatabase = env.getProperty("app.init-db", "false");
        
        if (!Boolean.parseBoolean(initDatabase)) {
            // Skip initialization
            return null;
        }
        
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema-mysql.sql"));
        populator.addScript(new ClassPathResource("data-mysql.sql"));
        populator.setSeparator(";");
        initializer.setDatabasePopulator(populator);
        
        return initializer;
    }
} 