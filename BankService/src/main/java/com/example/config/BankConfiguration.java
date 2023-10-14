package com.example.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankConfiguration {
    @Bean
    ModelMapper getMapper() {
        return new ModelMapper();
    }
}
