package com.example.services;

import com.example.dto.ResponseModel;
import com.example.dto.account.AccountRequest;
import com.example.dto.account.AccountUpdateRequest;
import com.example.dto.request.OperationRequest;
import com.example.dto.transaction.TransactionCreateRequest;
import com.example.dto.checking.CheckingAccountResponseModel;
import com.example.entity.Account;
import com.example.entity.CheckingAccount;
import com.example.entity.Transaction;
import com.example.enums.AccountStatus;
import com.example.enums.TransactionType;
import com.example.repository.AccountRepository;
import com.example.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CheckingAccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionService transactionService;




    public ResponseEntity<?> create(AccountRequest accountRequest,Long userId) {

        try {
            Account checkingAccount = new CheckingAccount();
            checkingAccount.setAccountNumber(Util.generateAccountNum());
            checkingAccount.setAccountStatus(AccountStatus.PENDING);
            checkingAccount.setBalance(checkingAccount.getBalance() == null ? BigDecimal.ZERO : checkingAccount.getBalance());
            checkingAccount.setUserId(userId);
            checkingAccount.setBranchId(accountRequest.getBranchId());
            checkingAccount.setCreatedDate(LocalDateTime.now());
            checkingAccount.setIsDeleted(false);
            accountRepository.save(checkingAccount);
            return ResponseEntity.status(HttpStatus.OK).body("Checking account has been created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account has not been created");
        }
    }

    public ResponseEntity<?> update(AccountUpdateRequest accountUpdateRequest) {
        try {
            Optional<Account> checkingAccountOptional = accountRepository.findByAccountNumber(accountUpdateRequest.getAccnumber());
            if (!checkingAccountOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account doesn't exist");
            }
            Account checkingAccount = checkingAccountOptional.get();
            checkingAccount.setAccountStatus(accountUpdateRequest.getAccountStatus());
            accountRepository.save(checkingAccount);
            return ResponseEntity.status(HttpStatus.OK).body("Checking account has been updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account has not been updated");
        }
    }

    public ResponseModel<Account> withdraw(OperationRequest operationRequest) {
        ResponseModel<Account> responseModel = new ResponseModel<>();
        try {
            Account checkingAccount;
            Optional<Account> checkingAccountOptional = accountRepository.findByAccountNumber(operationRequest.getAccountNum());
            if (!checkingAccountOptional.isPresent()) {
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

            TransactionCreateRequest transactionCreateRequest = new TransactionCreateRequest();
            transactionCreateRequest.setAccountNumber(checkingAccount.getAccountNumber());
            transactionCreateRequest.setAmount(operationRequest.getAmount());
            transactionCreateRequest.setPreviousBalance(previousBalance);
            transactionCreateRequest.setCurrentBalance(checkingAccount.getBalance());
            transactionCreateRequest.setTransactionType(TransactionType.WITHDRAW);

            ResponseModel<Transaction> response = transactionService.save(transactionCreateRequest);
            if (!response.getSuccess()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Transaction was not created");
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
            Optional<Account> checkingAccountOptional = accountRepository.findByAccountNumber(operationRequest.getAccountNum());
            if (!checkingAccountOptional.isPresent()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Account doesn't exist");
                return responseModel;
            }
            checkingAccount = checkingAccountOptional.get();
            BigDecimal previousBalance = checkingAccount.getBalance();
            checkingAccount.setBalance(previousBalance.add(operationRequest.getAmount()));
            checkingAccount = accountRepository.save(checkingAccount);

            TransactionCreateRequest transactionCreateRequest = new TransactionCreateRequest();
            transactionCreateRequest.setAccountNumber(checkingAccount.getAccountNumber());
            transactionCreateRequest.setAmount(operationRequest.getAmount());
            transactionCreateRequest.setPreviousBalance(previousBalance);
            transactionCreateRequest.setCurrentBalance(checkingAccount.getBalance());
            transactionCreateRequest.setTransactionType(TransactionType.DEPOSIT);

            ResponseModel<Transaction> response = transactionService.save(transactionCreateRequest);
            if (!response.getSuccess()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Transaction was not created");
                return responseModel;
            }
            checkingAccount.getTransactions().add(response.getData());
            checkingAccount = accountRepository.save(checkingAccount);
            responseModel.setSuccess(true);
            responseModel.setData(checkingAccount);
            responseModel.setMessage("Deposit successfull");
            return responseModel;
        } catch (Exception e) {
            responseModel.setSuccess(false);
            responseModel.setMessage("Deposit failed");
            return responseModel;
        }



    }



    public ResponseModel<Account> getCheckingAccountByAccNum(String accountNum) {
        ResponseModel<Account> responseModel = new ResponseModel<>();
        Optional<Account> optionalCheckingAccount = accountRepository.findByAccountNumber(accountNum);
        if (!optionalCheckingAccount.isPresent()) {
            responseModel.setSuccess(false);
            responseModel.setMessage("Account not found");
            return responseModel;
        }
        CheckingAccountResponseModel checkingResponseModel = new CheckingAccountResponseModel();

        Account checkingAccount = optionalCheckingAccount.get();
        checkingResponseModel.setAccountNumber(checkingAccount.getAccountNumber());
        checkingResponseModel.setUserId(checkingAccount.getUserId());
        checkingResponseModel.setAccountStatus(checkingAccount.getAccountStatus().toString());
        checkingResponseModel.setBalance(checkingAccount.getBalance());

        responseModel.setSuccess(true);
        responseModel.setData(checkingAccount);
        return responseModel;

    }
    public ResponseModel<List<Account>> getCheckingAccountsByUserId(Long userid) {
        ResponseModel<List<Account>> responseModel = new ResponseModel<>();
        List<Account> list = accountRepository.findByUserId(userid);
        if (list==null) {
            responseModel.setSuccess(false);
            responseModel.setMessage("Account not found");
            return responseModel;
        }
        responseModel.setSuccess(true);
        responseModel.setData(list);
        return responseModel;

    }
}
