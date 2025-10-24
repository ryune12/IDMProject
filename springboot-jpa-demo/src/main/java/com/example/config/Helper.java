package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "com.example")
public class Helper {
    // Global configuration beans can go here if needed
    @Bean
    public String appMessage() {
        return "Welcome to the User Management System!";
    }

    public Map<String, Object> buildResponse(HttpStatus status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("message", message);
        response.put("status", status.value());
        return response;
    }

}
