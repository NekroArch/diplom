package org.example;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(scanBasePackages = "org.example")
@OpenAPIDefinition
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
