package com.puppies.infrastructure.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

@TestConfiguration
public class TestContainersConfig {

    @Bean
    public PostgreSQLContainer<?> postgreSQLContainer() throws InterruptedException {
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.3"))
                .withDatabaseName("testdb")
                .withUsername("testuser")
                .withPassword("testpass");

        postgres.wait();
        postgres.start();

        System.setProperty("DB_URL", postgres.getJdbcUrl());
        System.setProperty("DB_USERNAME", postgres.getUsername());
        System.setProperty("DB_PASSWORD", postgres.getPassword());
        return postgres;
    }
}

