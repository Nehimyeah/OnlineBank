package com.example.controller;

import com.example.dto.ResponseModel;
import com.example.dto.request.TransactionRequest;
import com.example.entity.Transaction;
import com.example.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "*")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

//    @GetMapping("/list/{userid}")
//    public ResponseModel<List<Transaction>> listOfTransactions(@PathVariable String accountNumber){
//        return transactionService.getAllTransactionsByAccountNum(accountNumber);
//    }


}
