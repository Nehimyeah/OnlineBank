package com.example.entity;

import com.example.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


@Setter
@Getter
@Entity
public class Transaction{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal amount;
    private Date CreatedDate;
    private TransactionStatus transactionStatus;

    @OneToOne(cascade = CascadeType.ALL)
    private Account account;



}
