package com.example.services;

import com.example.dto.ResponseModel;
import com.example.dto.request.TransactionRequest;
import com.example.dto.response.GeneralResponseModel;
import com.example.entity.CheckingAccount;
import com.example.entity.Transaction;
import com.example.repository.AccountRepository;
import com.example.repository.CheckingAccountRepository;
import com.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class TransactionService {
    
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountRepository accountRepository;

//    public ResponseModel<List<Transaction>> getAllTransactionsByAccountNum(String accountNumber){
//        ResponseModel<List<Transaction>> responseModel = new ResponseModel<>();
//        try{
//           List<Transaction> list = transactionRepository.findAllByAccountId(UUID.fromString(accountNumber));
//
//            responseModel.setSuccess(true);
//            responseModel.setData(list);
//            return responseModel;
//        }catch (Exception e){
//            responseModel.setSuccess(false);
//            responseModel.setMessage("Transaction list failed");
//            return responseModel;
//        }
//    }





    public ResponseModel<Transaction> save(TransactionRequest transactionRequest) {
        ResponseModel<Transaction> responseModel =new ResponseModel<>();
        Transaction transaction;
        try {
            transaction = new Transaction();
            transaction.setAccountType(transactionRequest.getAccountType());
            transaction.setAccountNumber(transactionRequest.getAccountNumber());
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
        responseModel.setMessage("Transaction has been saved");
        responseModel.setData(transaction);
        return responseModel;
    }

}
