package com.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication

public class BankServiceApplications
{
    public static void main( String[] args )
    {

        ConfigurableApplicationContext context =  SpringApplication.run(BankServiceApplications.class, args);

        ApplicationContextProvider.setContext(context);


    }
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }



}
