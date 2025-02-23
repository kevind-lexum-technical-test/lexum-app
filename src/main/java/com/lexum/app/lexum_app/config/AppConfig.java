package com.lexum.app.lexum_app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${application.database.docs-table-name}")
    private String docsTableName;

    public String getDocsTableName() {
        return docsTableName;
    }
}

