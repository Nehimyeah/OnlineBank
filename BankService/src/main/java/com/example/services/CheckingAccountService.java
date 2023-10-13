package com.example.services;

import com.example.dto.ResponseModel;
import com.example.dto.request.checking.CheckingAccountRequest;
import com.example.dto.request.OperationRequest;
import com.example.dto.request.TransactionRequest;
import com.example.entity.Account;
import com.example.entity.CheckingAccount;
import com.example.entity.Transaction;
import com.example.enums.AccountStatus;
import com.example.enums.AccountType;
import com.example.enums.TransactionStatus;
import com.example.enums.TransactionType;
import com.example.repository.AccountRepository;
import com.example.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CheckingAccountService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionService transactionService;

    public ResponseEntity<?> create(CheckingAccountRequest checkingAccountRequest) {

        try {
            Account checkingAccount = new CheckingAccount();
            checkingAccount.setAccountNumber(Util.generateAccountNum());
            checkingAccount.setAccountStatus(AccountStatus.PENDING);
            checkingAccount.setBalance(checkingAccount.getBalance() == null ? BigDecimal.ZERO : checkingAccount.getBalance());
            checkingAccount.setUserId(checkingAccountRequest.getUserId());
            checkingAccount.setBranchId(checkingAccountRequest.getBranchId());
            checkingAccount.setCreatedBy(checkingAccountRequest.getCreatedBy());
            checkingAccount.setCreatedDate(LocalDateTime.now());
            checkingAccount.setIsDeleted(false);
            accountRepository.save(checkingAccount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account has not been created");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Checking account has been created successfully");
    }

    public ResponseEntity<?> update(CheckingAccountRequest checkingAccountRequest) {
        try {
            Optional<Account> checkingAccountOptional = accountRepository.findById(checkingAccountRequest.getAccountId());
            if (!checkingAccountOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account doesn't exist");
            }
            Account checkingAccount = checkingAccountOptional.get();
            checkingAccount.setAccountStatus(checkingAccountRequest.getAccountStatus());
            checkingAccount.setBranchId(checkingAccountRequest.getBranchId());
            checkingAccount.setIsDeleted(checkingAccountRequest.getIsDeleted());
            checkingAccount.setDeletedBy(checkingAccountRequest.getDeletedBy());
            checkingAccount.setDeletedDate(LocalDateTime.now());
            accountRepository.save(checkingAccount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account has not been updated");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Checking account has been updated successfully");
    }

    public ResponseModel<Account> withdraw(OperationRequest operationRequest) {
        ResponseModel<Account> responseModel = new ResponseModel<>();
        try {
            Account checkingAccount;
            Optional<Account> checkingAccountOptional = accountRepository.findById(UUID.fromString(operationRequest.getAccountId()));
            if (checkingAccountOptional.isPresent()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Account doesn't exist");
                return responseModel;
            }

            checkingAccount = checkingAccountOptional.get();

            // validate account
            if (!Util.validate(checkingAccount, operationRequest.getAmount())) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Balance is not sufficient ");
                return responseModel;
            }
            BigDecimal previousBalance = checkingAccount.getBalance();
            checkingAccount.setBalance(previousBalance.subtract(operationRequest.getAmount()));
            checkingAccount = accountRepository.save(checkingAccount);
            TransactionRequest transactionRequest = new TransactionRequest();
            transactionRequest.setAccountNumber(checkingAccount.getAccountNumber());
            transactionRequest.setAmount(operationRequest.getAmount());
            transactionRequest.setPreviousBalance(previousBalance);
            transactionRequest.setCurrentBalance(checkingAccount.getBalance());
            transactionRequest.setAccountType(AccountType.CHECKING);
            transactionRequest.setTransactionStatus(TransactionStatus.APPROVED);
            transactionRequest.setTransactionType(TransactionType.WITHDRAW);
            transactionRequest.setAccount(checkingAccount);
            ResponseModel<Transaction> response = transactionService.save(transactionRequest);
            if (!response.getSuccess()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Transaction failed");
                return responseModel;
            }
            checkingAccount.getTransactions().add(response.getData());
            checkingAccount = accountRepository.save(checkingAccount);
            responseModel.setSuccess(true);
            responseModel.setData(checkingAccount);
            responseModel.setMessage("Withdraw successfull");
            return responseModel;
        } catch (Exception e) {
            responseModel.setSuccess(false);
            responseModel.setMessage("Withdraw failed");
            return responseModel;
        }

    }

    public ResponseModel<Account> deposit(OperationRequest operationRequest) {
        Account checkingAccount;
        ResponseModel<Account> responseModel = new ResponseModel<>();
        try {
            Optional<Account> checkingAccountOptional = accountRepository.findById(UUID.fromString(operationRequest.getAccountId()));
            if (!checkingAccountOptional.isPresent()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Account doesn't exist");
                return responseModel;
            }
            checkingAccount = checkingAccountOptional.get();
            BigDecimal previousBalance = checkingAccount.getBalance();
            checkingAccount.setBalance(previousBalance.add(operationRequest.getAmount()));
            checkingAccount = accountRepository.save(checkingAccount);
            TransactionRequest transactionRequest = new TransactionRequest();
            transactionRequest.setAccountNumber(checkingAccount.getAccountNumber());
            transactionRequest.setAmount(operationRequest.getAmount());
            transactionRequest.setPreviousBalance(previousBalance);
            transactionRequest.setCurrentBalance(checkingAccount.getBalance());
            transactionRequest.setAccountType(AccountType.CHECKING);
            transactionRequest.setTransactionType(TransactionType.DEPOSIT);
            transactionRequest.setTransactionStatus(TransactionStatus.APPROVED);
            transactionRequest.setAccount(checkingAccount);
            ResponseModel<Transaction> response = transactionService.save(transactionRequest);
            if (!response.getSuccess()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Transaction was not created");
                return responseModel;
            }
            checkingAccount.getTransactions().add(response.getData());
            checkingAccount = accountRepository.save(checkingAccount);

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



//    public ResponseModel<Account> getCheckingAccount(String accountId) {
//        ResponseModel<Account> responseModel = new ResponseModel<>();
//        Optional<Account> optionalCheckingAccount = accountRepository.findById(accountId);
//        if (!optionalCheckingAccount.isPresent()) {
//            responseModel.setSuccess(false);
//            responseModel.setMessage("Account not found");
//            return responseModel;
//        }
//        CheckingAccountResponseModel checkingResponseModel = new CheckingAccountResponseModel();
//
//        Account checkingAccount = optionalCheckingAccount.get();
//        checkingResponseModel.setAccountNumber(checkingAccount.getAccountNumber());
//        checkingResponseModel.setUserId(checkingAccount.getUserId());
//        checkingResponseModel.setAccountStatus(checkingAccount.getAccountStatus().toString());
//        checkingResponseModel.setBalance(checkingAccount.getBalance());
//
//        responseModel.setSuccess(true);
//        responseModel.setData(checkingAccount);
//        return responseModel;
//
//    }
}
