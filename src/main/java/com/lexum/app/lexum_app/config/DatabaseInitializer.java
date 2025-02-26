package com.lexum.app.lexum_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class DatabaseInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Autowired
    private AppConfig appConfig;

    @Bean
    public ApplicationRunner initDatabase(JdbcTemplate jdbcTemplate) {
        String tableName = appConfig.getDocsTableName();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (id SERIAL PRIMARY KEY, value TEXT NOT NULL)";
        String checkDataSQL = "SELECT COUNT(*) FROM " + tableName;
        String insertDataSQL = "INSERT INTO " + tableName + " (value) VALUES ('Lexum!')";

        return args -> {
            logger.info("Initializing database table: {}", tableName);
            logger.debug("Executing SQL Query: {}", createTableSQL);

            try {
                // Step 1: Create Table if not exists
                int rowsAffected = jdbcTemplate.update(createTableSQL);
                logger.info("Table '{}' initialization completed. Rows affected: {}", tableName, rowsAffected);

                // Step 2: Check if there is already data in the table
                Integer rowCount = jdbcTemplate.queryForObject(checkDataSQL, Integer.class);
                logger.info("Table '{}' contains {} rows.", tableName, rowCount);

                // Step 3: Insert data only if the table is empty
                if (rowCount != null && rowCount == 0) {
                    logger.info("No data found in '{}'. Inserting default value.", tableName);
                    jdbcTemplate.update(insertDataSQL);
                    logger.info("Default value inserted into table '{}'.", tableName);
                } else {
                    logger.info("Table '{}' already contains data. Skipping insert.", tableName);
                }

            } catch (Exception e) {
                logger.error("Error initializing table '{}': {}", tableName, e.getMessage(), e);
            }
        };
    }
}
