package com.lexum.app.lexum_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseInitializer {

    @Autowired
    private AppConfig appConfig;

    @Bean
    public ApplicationRunner initDatabase(JdbcTemplate jdbcTemplate) {
        String tableName = appConfig.getDocsTableName();
        String sql = "INSERT INTO " + tableName + " (value) SELECT 'Extra docs' WHERE NOT EXISTS (SELECT 1 FROM " + tableName + ")";
        return args -> jdbcTemplate.update(sql);
    }
}
