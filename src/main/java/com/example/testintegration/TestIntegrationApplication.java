package com.example.testintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TestIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestIntegrationApplication.class, args);
    }

}
