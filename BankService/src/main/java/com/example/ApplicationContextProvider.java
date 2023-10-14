package com.example;

import com.example.repository.AccountRepository;
import com.example.repository.CheckingAccountRepository;
import com.example.repository.LoanAccountRepository;
import com.example.repository.SavingsAccountRepository;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class ApplicationContextProvider {
    private static ConfigurableApplicationContext context;

    public static void setContext(ConfigurableApplicationContext context) {
        ApplicationContextProvider.context = context;
    }

    public static ConfigurableApplicationContext getContext() {

        return context;
    }
    public static AccountRepository accountRepository(String repository){
        switch (repository){
            case "checking" :
                return context.getBean(CheckingAccountRepository.class);
            case "loan":
                return context.getBean(LoanAccountRepository.class);
            case "savings":
                return context.getBean(SavingsAccountRepository.class);
        }
        return null;
    }


}
