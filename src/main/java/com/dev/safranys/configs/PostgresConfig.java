package com.dev.safranys.configs;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.sql.DataSource;

/**
 * Configures the PostgreSQL {@link DataSource}.  The application
 * properties are defined in {@code application.properties} under the
 * {@code spring.datasource} prefix.  This bean is used by Spring
 * Boot's JPA auto‑configuration.
 */
@Configuration
@EnableConfigurationProperties(CosmosProperties.class)
public class PostgresConfig {

    private final CosmosProperties cosmosProperties;

    public PostgresConfig(CosmosProperties cosmosProperties) {
        this.cosmosProperties = cosmosProperties;
    }

    @Bean
    public DataSource dataSource() {
        // Build a HikariDataSource using the properties bound to CosmosProperties.
        // The driver class name is required for PostgreSQL.
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .driverClassName("org.postgresql.Driver")
                .url(cosmosProperties.getUrl())
                .username(cosmosProperties.getUsername())
                .password(cosmosProperties.getPassword())
                .build();
    }

    @PostConstruct
    private void validateProperties() {
        // Ensure essential properties are present to avoid startup failures.
        if (cosmosProperties.getUrl() == null || cosmosProperties.getUrl().isBlank()) {
            throw new IllegalStateException("spring.datasource.url must be configured");
        }
        if (cosmosProperties.getUsername() == null || cosmosProperties.getUsername().isBlank()) {
            throw new IllegalStateException("spring.datasource.username must be configured");
        }
        if (cosmosProperties.getPassword() == null) {
            throw new IllegalStateException("spring.datasource.password must be configured");
        }
    }
}