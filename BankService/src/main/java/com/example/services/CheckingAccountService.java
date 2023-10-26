package com.example.services;

import com.example.dto.ResponseModel;
import com.example.dto.account.AccountRequest;
import com.example.dto.account.AccountTransferRequest;
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
            checkingAccount.setAccountStatus(AccountStatus.ACTIVE);
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

    public ResponseEntity<?> withdraw(OperationRequest operationRequest) {
        try {
            Account checkingAccount;
            Optional<Account> checkingAccountOptional = accountRepository.findByAccountNumber(operationRequest.getAccountNum());
            if (!checkingAccountOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            checkingAccount = checkingAccountOptional.get();

            // validate account
            if (!Util.validate(checkingAccount, operationRequest.getAmount())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Balance is not sufficient");
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction was not created");
            }

            checkingAccount.getTransactions().add(response.getData());
            accountRepository.save(checkingAccount);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    public ResponseEntity<?> deposit(OperationRequest operationRequest) {
        Account checkingAccount;
        try {
            Optional<Account> checkingAccountOptional = accountRepository.findByAccountNumber(operationRequest.getAccountNum());
            if (!checkingAccountOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction is not created.");
            }
            checkingAccount.getTransactions().add(response.getData());
            accountRepository.save(checkingAccount);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }



    }
    public ResponseEntity<?> transferMoney(AccountTransferRequest accountTransferRequest) {
        ResponseModel<Account> responseModel = new ResponseModel<>();
        try {
            Account fromAccount;
            Optional<Account> fromAccountOptional = accountRepository.findByAccountNumber(accountTransferRequest.getFromAccountNum());
            if (!fromAccountOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            fromAccount = fromAccountOptional.get();
            if(!(fromAccount instanceof CheckingAccount)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sender is not a checking account");
            }
            else if (!(String.valueOf(fromAccount.getAccountStatus()).equalsIgnoreCase("active"))){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sender account is not active");
            }



            Account toAccount;
            Optional<Account> toAccountOptional = accountRepository.findByAccountNumber(accountTransferRequest.getToAccountNum());
            if (!toAccountOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            toAccount = toAccountOptional.get();
            if(!(toAccount instanceof CheckingAccount)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Receiver is not a checking account");
            } else if (!(String.valueOf(toAccount.getAccountStatus()).equalsIgnoreCase("active"))){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Receiver account is not active");
            }

            toAccount = toAccountOptional.get();

            // validate account
            if (!Util.validate(fromAccount, accountTransferRequest.getAmount())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Balance is not sufficient in Account: "+ fromAccount.getAccountNumber());
            }
            BigDecimal previousFromAccountBalance = fromAccount.getBalance();
            fromAccount.setBalance(previousFromAccountBalance.subtract(accountTransferRequest.getAmount()));
            fromAccount = accountRepository.save(fromAccount);

            BigDecimal previoustoAccountBalance = toAccount.getBalance();
            toAccount.setBalance(previoustoAccountBalance.add(accountTransferRequest.getAmount()));
            toAccount = accountRepository.save(toAccount);

            TransactionCreateRequest fromAccountTransactionCreateRequest = new TransactionCreateRequest();
            fromAccountTransactionCreateRequest.setAccountNumber(fromAccount.getAccountNumber());
            fromAccountTransactionCreateRequest.setAmount(accountTransferRequest.getAmount());
            fromAccountTransactionCreateRequest.setPreviousBalance(previousFromAccountBalance);
            fromAccountTransactionCreateRequest.setCurrentBalance(fromAccount.getBalance());
            fromAccountTransactionCreateRequest.setInfo("Money transfer from Account: " + fromAccount.getAccountNumber() + " to Account: " + toAccount.getAccountNumber());
            fromAccountTransactionCreateRequest.setTransactionType(TransactionType.TRANSFERTO);

            ResponseModel<Transaction> fromAccountresponse = transactionService.save(fromAccountTransactionCreateRequest);
            if (!fromAccountresponse.getSuccess()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("FromAccount sender transaction was not created");
            }
            TransactionCreateRequest toAccountTransactionCreateRequest = new TransactionCreateRequest();
            toAccountTransactionCreateRequest.setAccountNumber(toAccount.getAccountNumber());
            toAccountTransactionCreateRequest.setAmount(accountTransferRequest.getAmount());
            toAccountTransactionCreateRequest.setPreviousBalance(previoustoAccountBalance);
            toAccountTransactionCreateRequest.setCurrentBalance(toAccount.getBalance());
            toAccountTransactionCreateRequest.setInfo("Money received from Account: " + fromAccount.getAccountNumber());
            toAccountTransactionCreateRequest.setTransactionType(TransactionType.RECEIVEFROM);

            ResponseModel<Transaction> toAccountresponse = transactionService.save(toAccountTransactionCreateRequest);
            if (!toAccountresponse.getSuccess()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ToAccount receiver transaction was not created");
            }

            fromAccount.getTransactions().add(fromAccountresponse.getData());
            fromAccount = accountRepository.save(fromAccount);

            toAccount.getTransactions().add(toAccountresponse.getData());
            toAccount = accountRepository.save(toAccount);
            return ResponseEntity.status(HttpStatus.OK).body("Money transfer was successful");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
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
