package com.example.services;

import com.example.dto.ResponseModel;
import com.example.dto.request.CheckingAccountRequest;
import com.example.dto.request.OperationRequest;
import com.example.dto.request.TransactionRequest;
import com.example.dto.response.CheckingAccountResponseModel;
import com.example.dto.response.GeneralResponseModel;
import com.example.entity.Account;
import com.example.entity.CheckingAccount;
import com.example.entity.Transaction;
import com.example.enums.AccountStatus;
import com.example.enums.AccountType;
import com.example.enums.TransactionStatus;
import com.example.enums.TransactionType;
import com.example.repository.CheckingAccountRepository;
import com.example.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CheckingAccountService {


    @Autowired
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    TransactionService transactionService;

    public ResponseEntity<?> create(CheckingAccountRequest checkingAccountRequest) {


        try {
            CheckingAccount checkingAccount = new CheckingAccount();

            checkingAccount.setAccountNumber(Util.generateAccountNum());
            checkingAccount.setAccountStatus(AccountStatus.PENDING);
            checkingAccount.setBalance(checkingAccount.getBalance()==null? BigDecimal.ZERO:checkingAccount.getBalance());
            checkingAccount.setUserId(checkingAccountRequest.getUserId());
            checkingAccount.setBranchId(checkingAccountRequest.getBranchId());
            checkingAccount.setCreatedBy(checkingAccountRequest.getCreatedBy());
            checkingAccount.setCreatedDate(LocalDateTime.now());
       //     checkingAccount.setDeletedBy(checkingAccountRequest.getDeletedBy());
       //     checkingAccount.setDeletedDate(LocalDateTime.now());
            checkingAccount.setIsDeleted(false);
            checkingAccountRepository.save(checkingAccount);


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account has not been created");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Checking account has been created successfully");

    }

    public ResponseEntity<?> update(CheckingAccountRequest checkingAccountRequest){
        try {
            Optional<CheckingAccount> checkingAccountOptional = checkingAccountRepository.findById(checkingAccountRequest.getAccountId());
            if(!checkingAccountOptional.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account doesn't exist");
            }
            CheckingAccount checkingAccount = checkingAccountOptional.get();
            checkingAccount.setAccountStatus(checkingAccountRequest.getAccountStatus());
            checkingAccount.setBranchId(checkingAccountRequest.getBranchId());
            checkingAccount.setIsDeleted(checkingAccountRequest.getIsDeleted());
            checkingAccount.setDeletedBy(checkingAccountRequest.getDeletedBy());
            checkingAccount.setDeletedDate(LocalDateTime.now());
            checkingAccountRepository.save(checkingAccount);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account has not been updated");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Checking account has been updated successfully");
    }

    public ResponseModel<CheckingAccount>  withdraw(OperationRequest operationRequest) {
        CheckingAccount checkingAccount;
        ResponseModel<CheckingAccount> responseModel = new ResponseModel<>();
        try {
            Optional<CheckingAccount> checkingAccountOptional = checkingAccountRepository.findById(UUID.fromString(operationRequest.getAccountId()));
            if (!checkingAccountOptional.isPresent()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Account doesn't exist");
                return responseModel;
            }

            checkingAccount = checkingAccountOptional.get();

            // validate account
            if (!validate(checkingAccount, operationRequest.getAmount())) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Balance is not sufficient ");
                return responseModel;
            }
            BigDecimal previousBalance = checkingAccount.getBalance();
            checkingAccount.setBalance(previousBalance.subtract(operationRequest.getAmount()));
            checkingAccount = checkingAccountRepository.save(checkingAccount);

            TransactionRequest transactionRequest = new TransactionRequest();
            transactionRequest.setAccountNumber(checkingAccount.getAccountNumber());
            transactionRequest.setAmount(operationRequest.getAmount());
            transactionRequest.setPreviousBalance(previousBalance);
            transactionRequest.setCurrentBalance(checkingAccount.getBalance());
            transactionRequest.setAccountType(AccountType.CHECKING);
            transactionRequest.setTransactionStatus(TransactionStatus.APPROVED);
            transactionRequest.setTransactionType(TransactionType.WITHDRAW);
            transactionRequest.setAccount(checkingAccount);
            GeneralResponseModel<Transaction> response = transactionService.save(transactionRequest);
            if (!response.getSuccess()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Transaction failed");
                return responseModel;
            }
            checkingAccount.getTransactions().add(response.getData());
            checkingAccount = checkingAccountRepository.save(checkingAccount);

        } catch (Exception e) {
            responseModel.setSuccess(false);
            responseModel.setMessage("Withdraw failed");
            return responseModel;
        }
        responseModel.setSuccess(true);
        responseModel.setData(checkingAccount);
        responseModel.setMessage("Withdraw successfull");
        return responseModel;
    }
    public ResponseModel<CheckingAccount> deposit(OperationRequest operationRequest) {
        CheckingAccount checkingAccount;
        ResponseModel<CheckingAccount> responseModel = new ResponseModel<>();
        try {
            Optional<CheckingAccount> checkingAccountOptional = checkingAccountRepository.findById(UUID.fromString(operationRequest.getAccountId()));
            if (!checkingAccountOptional.isPresent()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Account doesn't exist");
                return responseModel;
            }

            checkingAccount = checkingAccountOptional.get();

            BigDecimal previousBalance = checkingAccount.getBalance();
            checkingAccount.setBalance(previousBalance.add(operationRequest.getAmount()));
            checkingAccount = checkingAccountRepository.save(checkingAccount);

            TransactionRequest transactionRequest = new TransactionRequest();
            transactionRequest.setAccountNumber(checkingAccount.getAccountNumber());
            transactionRequest.setAmount(operationRequest.getAmount());
            transactionRequest.setPreviousBalance(previousBalance);
            transactionRequest.setCurrentBalance(checkingAccount.getBalance());
            transactionRequest.setAccountType(AccountType.CHECKING);
            transactionRequest.setTransactionType(TransactionType.DEPOSIT);
            transactionRequest.setTransactionStatus(TransactionStatus.APPROVED);
            transactionRequest.setAccount(checkingAccount);

            GeneralResponseModel<Transaction> response = transactionService.save(transactionRequest);
            if (!response.getSuccess()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Transaction was not created");
                return responseModel;
            }
            checkingAccount.getTransactions().add(response.getData());

            checkingAccount = checkingAccountRepository.save(checkingAccount);

        } catch (Exception e) {
            responseModel.setSuccess(false);
            responseModel.setMessage("Deposit failed");
            return responseModel;
        }
        responseModel.setSuccess(true);
        responseModel.setData(checkingAccount);
        responseModel.setMessage("Deposit succussfull");
        return responseModel;


    }

    public boolean validate(CheckingAccount checkingAccount,BigDecimal existingAmount){
        if(checkingAccount.getBalance().compareTo(BigDecimal.ZERO)<0 || checkingAccount.getBalance().compareTo(existingAmount)<0){
            return false;
        }
        return true;
    }

    public ResponseModel<CheckingAccountResponseModel> getCheckingAccount(Long id) {
        ResponseModel<CheckingAccountResponseModel> responseModel = new ResponseModel<>();
        Optional<CheckingAccount> optionalCheckingAccount = checkingAccountRepository.findByUserId(id);
        if(!optionalCheckingAccount.isPresent()){
            responseModel.setSuccess(false);
            responseModel.setMessage("Account not found");
            return responseModel;
        }
        CheckingAccountResponseModel checkingResponseModel = new CheckingAccountResponseModel();
        CheckingAccount checkingAccount = optionalCheckingAccount.get();
        checkingResponseModel.setAccountNumber(checkingAccount.getAccountNumber());
        checkingResponseModel.setUserId(checkingAccount.getUserId());
        checkingResponseModel.setAccountStatus(checkingAccount.getAccountStatus().toString());
        checkingResponseModel.setBalance(checkingAccount.getBalance());

        responseModel.setSuccess(true);
        responseModel.setData(checkingResponseModel);
        return responseModel;

    }
}
