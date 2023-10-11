package com.example.controller;

import com.example.dto.request.TransactionRequest;
import com.example.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "*")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody TransactionRequest transactionRequest){
        return transactionService.deposit(transactionRequest);
    }
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody TransactionRequest transactionRequest){
        return transactionService.withdraw(transactionRequest);
    }
}
