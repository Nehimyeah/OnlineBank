package com.example;

import com.example.repository.AccountRepository;
import com.example.repository.CheckingAccountRepository;
import com.example.repository.LoanAccountRepository;
import com.example.repository.SavingsAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication

public class BankServiceApplications
{
    public static void main( String[] args )
    {

        ConfigurableApplicationContext context =  SpringApplication.run(BankServiceApplications.class, args);

        ApplicationContextProvider.setContext(context);



    }



}
