package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

@Configuration
public class TestContainersConfig {
    @Bean
    public PostgreSQLContainer postgreSQLContainer(){
        return new PostgreSQLContainer<>("postgres")
                .withDatabaseName("pizzeria_db")
                .withUsername("pguser")
                .withPassword("pgpassword");
    }
}