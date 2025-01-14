package com.puppies.infrastructure.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

@TestConfiguration
public class TestContainersConfig {

    @Bean
    public PostgreSQLContainer<?> postgreSQLContainer() throws InterruptedException {
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.3"))
                .withDatabaseName("testdb")
                .withUsername("testuser")
                .withPassword("testpass")
                .waitingFor(
                        Wait.forLogMessage(".*database system is ready to accept connections.*\\s", 2)
                                .withStartupTimeout(Duration.ofSeconds(30))
                );;



        postgres.start();

        System.setProperty("DB_URL", postgres.getJdbcUrl());
        System.setProperty("DB_USERNAME", postgres.getUsername());
        System.setProperty("DB_PASSWORD", postgres.getPassword());
        return postgres;
    }
}

