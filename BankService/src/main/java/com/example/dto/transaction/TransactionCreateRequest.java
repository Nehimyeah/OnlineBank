package com.example.dto.transaction;

import com.example.enums.TransactionStatus;
import com.example.enums.AccountType;
import com.example.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionCreateRequest {
    private String accountNumber;
    private BigDecimal amount;
    private BigDecimal previousBalance;
    private BigDecimal currentBalance;
    private AccountType accountType;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;


}
