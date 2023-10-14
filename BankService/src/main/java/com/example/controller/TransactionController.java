package com.example.controller;

import com.example.entity.Transaction;
import com.example.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "*")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("/list/{accountNumber}")
    public List<Transaction> listOfTransactions(@PathVariable String accountNumber ){
        return transactionService.getAllTransactionsByAccountNum(accountNumber);
    }


}
