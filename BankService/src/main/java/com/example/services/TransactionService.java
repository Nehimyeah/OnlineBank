package com.example.services;

import com.example.dto.ResponseModel;
import com.example.dto.request.transaction.TransactionCreateRequest;
import com.example.entity.Account;
import com.example.entity.Transaction;
import com.example.repository.AccountRepository;
import com.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class TransactionService {
    
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountRepository accountRepository;


    public ResponseModel<Transaction> save(TransactionCreateRequest transactionCreateRequest) {
        ResponseModel<Transaction> responseModel =new ResponseModel<>();
        Transaction transaction;
        try {
            transaction = new Transaction();
            transaction.setPreviousBalance(transactionCreateRequest.getPreviousBalance());
            transaction.setAmount(transactionCreateRequest.getAmount());
            transaction.setTransactionType(transactionCreateRequest.getTransactionType());
            transaction.setCreatedDate(LocalDateTime.now());
            transaction = transactionRepository.save(transaction);

        } catch (Exception e) {
            responseModel.setSuccess(false);
            responseModel.setException(e);
            return responseModel;
        }
        responseModel.setSuccess(true);
        responseModel.setMessage("Transaction has been saved");
        responseModel.setData(transaction);
        return responseModel;
    }

    public List<Transaction> getAllTransactionsByAccountNum(String accountNumber) {
       Account account = accountRepository.findByAccountNumber(accountNumber).
                 orElseThrow(RuntimeException::new);
       return account.getTransactions();
    }


}
