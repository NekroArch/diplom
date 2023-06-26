package org.example.userservice;

import org.example.TestContainersConfig;
import org.example.userservice.config.DatabaseConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest(classes = {DatabaseConfig.class, TestContainersConfig.class})
class UserServiceApplicationTests {

    @Autowired
    @Container
    private PostgreSQLContainer postgreSQLContainer;
    @Test
    void contextLoads() {
    }

}
