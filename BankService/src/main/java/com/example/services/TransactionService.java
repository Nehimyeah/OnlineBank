package com.example.services;

import com.example.dto.request.TransactionRequest;
import com.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    
    @Autowired
    TransactionRepository transactionRepository;
    public ResponseEntity<?> deposit(TransactionRequest transactionRequest) {
        return ResponseEntity.status(HttpStatus.OK).body("Good");
    }

    public ResponseEntity<?> withdraw(TransactionRequest transactionRequest) {
        return ResponseEntity.status(HttpStatus.OK).body("Good");
    }
}
