package com.example.errorhandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableSchemaRegistryClient
public class ErrorHandlerSinkApplication {
    public static void main(String[] args) {
        SpringApplication.run(ErrorHandlerSinkApplication.class, args);
    }
}
