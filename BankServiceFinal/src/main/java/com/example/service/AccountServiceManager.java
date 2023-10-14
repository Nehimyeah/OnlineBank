package com.example.service;

import com.example.annotation.EntityHandler;
import com.example.domain.Account;
import com.example.dto.AccountDto;
import com.example.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceManager  {
    private final AccountRepository accountRepository;
    private final ApplicationContext appcontext;

//    public AccountDto get(String accountNumber) {
//        Optional<Account> optAccount = accountRepository.findByAccountNumber(accountNumber);
//        Account acc = optAccount.orElseThrow(() -> new RuntimeException("The account doesn't exist"));
//        EntityHandler annotation = acc.getClass().getAnnotation(EntityHandler.class);
//        AccountService<AccountDto, Account> accountService = appcontext.getBean(annotation.value(), AccountService.class);
//
//        return accountService.get(acc);
//    }
}
