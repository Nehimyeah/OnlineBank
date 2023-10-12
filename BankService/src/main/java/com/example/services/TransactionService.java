package com.example.services;

import com.example.dto.ResponseModel;
import com.example.dto.request.TransactionRequest;
import com.example.dto.response.CheckingAccountResponseModel;
import com.example.dto.response.GeneralResponseModel;
import com.example.entity.Account;
import com.example.entity.CheckingAccount;
import com.example.entity.Transaction;

import com.example.enums.AccountType;
import com.example.enums.TransactionStatus;
import com.example.enums.TransactionType;
import com.example.repository.CheckingAccountRepository;
import com.example.repository.TransactionRepository;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {
    
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    public ResponseModel<List<Transaction>> getAllTransactionsByAccountNum(Long userId){
        ResponseModel<List<Transaction>> responseModel = new ResponseModel<>();
        try{
            Optional<CheckingAccount> optionalCheckingAccount = checkingAccountRepository.findByUserId(userId);
            if(!optionalCheckingAccount.isPresent()){
                responseModel.setSuccess(false);
                responseModel.setMessage("Account not found");
                return responseModel;
            }
            CheckingAccount checkingAccount = optionalCheckingAccount.get();
            List<Transaction> transactionList = transactionRepository.findTransactionsByAccount(checkingAccount);
            responseModel.setSuccess(true);
            responseModel.setData(transactionList);
            return responseModel;
        }catch (Exception e){
            responseModel.setSuccess(false);
            responseModel.setMessage("Transaction list failed");
            return responseModel;
        }
    }





    public GeneralResponseModel<Transaction> save(TransactionRequest transactionRequest) {
        GeneralResponseModel<Transaction> responseModel =new GeneralResponseModel<>();
        Transaction transaction;
        try {
//            private AccountType accountType;
//            private BigDecimal previousBalance;
//            private BigDecimal currentBalance;
//            private BigDecimal amount;
//            private TransactionType transactionType;
//            private LocalDateTime createdDate;
//            private TransactionStatus transactionStatus;
//            private Account account;
            transaction = new Transaction();
            transaction.setAccountType(transactionRequest.getAccountType());
            transaction.setCurrentBalance(transactionRequest.getCurrentBalance());
            transaction.setPreviousBalance(transactionRequest.getPreviousBalance());
            transaction.setAmount(transactionRequest.getAmount());
            transaction.setTransactionType(transactionRequest.getTransactionType());
            transaction.setCreatedDate(LocalDateTime.now());
            transaction.setTransactionStatus(transactionRequest.getTransactionStatus());
            transaction.setAccount(transactionRequest.getAccount());
            transaction = transactionRepository.save(transaction);

        } catch (Exception e) {
            responseModel.setSuccess(false);
            responseModel.setException(e);
            return responseModel;
        }
        responseModel.setSuccess(true);
        responseModel.setMessage("Transaction was created");
        responseModel.setData(transaction);
        return responseModel;
    }

}
