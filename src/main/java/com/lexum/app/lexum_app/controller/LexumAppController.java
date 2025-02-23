package com.lexum.app.lexum_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LexumAppController {

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Ok");
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/data")
    public ResponseEntity<String> getData() {
        String result = (String) jdbcTemplate.queryForObject("SELECT value FROM my_table LIMIT 1", String.class);
        return ResponseEntity.ok(result);
    }

}
