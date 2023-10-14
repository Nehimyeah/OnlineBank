package com.example.dto.request;


import com.example.domain.Account;
import com.example.enums.AccountType;
import com.example.enums.TransactionStatus;
import com.example.enums.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class TransactionRequest {
    private String accountNumber;
    private BigDecimal amount;
    private BigDecimal previousBalance;
    private BigDecimal currentBalance;
    private AccountType accountType;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private Account account;



}
