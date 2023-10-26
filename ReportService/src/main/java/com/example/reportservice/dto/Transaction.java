package com.example.reportservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaction {

    private BigDecimal previousBalance;
    private BigDecimal amount;
    private String transactionType;
    private LocalDateTime createdDate;
    private String info;
}
