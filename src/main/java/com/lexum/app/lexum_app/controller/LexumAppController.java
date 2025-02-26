package com.lexum.app.lexum_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lexum.app.lexum_app.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/")
public class LexumAppController {

    // SLF4J Logger
    private static final Logger logger = LoggerFactory.getLogger(LexumAppController.class);

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        logger.info("Health check endpoint called.");
        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/unhealth")
    public ResponseEntity<String> notHealthCheck() {
        logger.error("Unhealth check endpoint called.");
        return ResponseEntity.ok("Ok");
    }

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/data")
    public ResponseEntity<String> getData() {
        String tableName = appConfig.getDocsTableName();
        String sql = "SELECT value FROM " + tableName + " LIMIT 1";

        logger.info("Fetching data from table: {}", tableName);
        logger.debug("Executing SQL Query: {}", sql);

        try {
            String result = jdbcTemplate.queryForObject(sql, String.class);
            if (result != null) {
                logger.info("Data retrieved successfully: {}", result);
                return ResponseEntity.ok(result);
            } else {
                logger.warn("No data found in table: {}", tableName);
                return ResponseEntity.status(204).body("No Data Found");
            }
        } catch (Exception e) {
            logger.error("Error fetching data from table: {}", tableName, e);
            return ResponseEntity.status(500).body("Error fetching data: " + e.getMessage());
        }
    }
}
