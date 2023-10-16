package com.example.controller;

import com.example.entity.Transaction;
import com.example.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(
        origins = "http://127.0.0.1:5173/",
        allowedHeaders = "*", maxAge = 3600,
        allowCredentials = "true"
)
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("/list/{accountNumber}")
    public List<Transaction> listOfTransactions(@PathVariable String accountNumber,String token){
        return transactionService.getAllTransactionsByAccountNum(accountNumber);
    }


}
