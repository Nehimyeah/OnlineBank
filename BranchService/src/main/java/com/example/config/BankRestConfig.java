package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BankRestConfig {


    @Bean
    public RestTemplate restTemplate(){

        return new RestTemplate();
    }

}
