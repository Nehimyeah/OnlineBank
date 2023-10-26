package com.example.reportservice.service;

import com.example.reportservice.dto.Transaction;

import java.util.List;

public interface ReportService {
    byte[] getStatements(String token, String accountNumber);

    List<Transaction> getTransaction(String accountNumber);
}
