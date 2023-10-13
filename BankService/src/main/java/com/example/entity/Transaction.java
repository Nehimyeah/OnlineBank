package com.example.entity;

import com.example.enums.TransactionStatus;
import com.example.enums.AccountType;
import com.example.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Setter
@Getter
@Entity
public class Transaction{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private String accountNumber;
    private BigDecimal previousBalance;
    private BigDecimal currentBalance;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private LocalDateTime createdDate;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    @ManyToOne
    private Account account;



}
