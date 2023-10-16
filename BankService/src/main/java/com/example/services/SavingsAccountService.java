package com.example.services;

import com.example.dto.ResponseModel;
import com.example.dto.request.OperationRequest;
import com.example.dto.account.AccountRequest;
import com.example.dto.account.AccountUpdateRequest;
import com.example.dto.transaction.TransactionCreateRequest;
import com.example.dto.savings.SavingsResponse;
import com.example.entity.*;
import com.example.enums.AccountStatus;
import com.example.enums.TransactionType;
import com.example.repository.AccountRepository;
import com.example.repository.AnnualAPYRepository;
import com.example.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class SavingsAccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AnnualAPYRepository apyRepository;

    @Autowired
    TransactionService transactionService;

    public ResponseEntity<?> create(AccountRequest accountRequest,Long userId) {
        try {
            Account savingsAccount = new SavingsAccount();

            savingsAccount.setAccountNumber(Util.generateAccountNum());
            savingsAccount.setAccountStatus(AccountStatus.PENDING);
            savingsAccount.setBalance(savingsAccount.getBalance()==null? BigDecimal.ZERO:savingsAccount.getBalance());
            if (accountRequest.getInterestRateId() != null) {
                Optional<AnnualAPY> optionalAnnualAPY = apyRepository.findById(accountRequest.getInterestRateId());
                if (!optionalAnnualAPY.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Annual APY id has not been provided");
                }
                ((SavingsAccount) savingsAccount).setAnnualAPY(optionalAnnualAPY.get().getAnnualAPY());
            }
            savingsAccount.setUserId(userId);
            savingsAccount.setBranchId(accountRequest.getBranchId());
            savingsAccount.setCreatedDate(LocalDateTime.now());
            savingsAccount.setIsDeleted(false);
            accountRepository.save(savingsAccount);
            return ResponseEntity.status(HttpStatus.OK).body("Savings account has been created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account has not been created");
        }
    }
    public ResponseEntity<?> update(AccountUpdateRequest accountUpdateRequest) {
        try {
            Account savingsAccount = getByAccountNumber(accountUpdateRequest.getAccnumber());
            savingsAccount.setAccountStatus(accountUpdateRequest.getAccountStatus());
//            savingsAccount.setIsDeleted(savingsAccountRequest.getIsDeleted());
//            savingsAccount.setDeletedBy(savingsAccountRequest.getDeletedBy());
            savingsAccount.setDeletedDate(LocalDateTime.now());
            accountRepository.save(savingsAccount);
            return ResponseEntity.status(HttpStatus.OK).body("Savings account has been updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account has not been updated");
        }
    }

    public Account getByAccountNumber(String accountNumber){
        Optional<Account> optionalSavingsAccount = accountRepository.findByAccountNumber(accountNumber);
        if (!optionalSavingsAccount.isPresent()) {
            throw new RuntimeException("Account doesn't exist");
        }
        Account account = optionalSavingsAccount.get();
        return account;
    }


    public ResponseModel<Account> withdraw(OperationRequest operationRequest) {
        ResponseModel<Account> responseModel = new ResponseModel<>();
        try {
            Account savingsAccount;
            Optional<Account> checkingAccountOptional = accountRepository.findByAccountNumber(operationRequest.getAccountNum());
            if (!checkingAccountOptional.isPresent()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Account doesn't exist");
                return responseModel;
            }
            savingsAccount = checkingAccountOptional.get();

            // validate account
            if (!Util.validate(savingsAccount, operationRequest.getAmount())) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Balance is not sufficient ");
                return responseModel;
            }
            BigDecimal previousBalance = savingsAccount.getBalance();
            savingsAccount.setBalance(previousBalance.subtract(operationRequest.getAmount()));
            savingsAccount = accountRepository.save(savingsAccount);

            TransactionCreateRequest transactionCreateRequest = new TransactionCreateRequest();
            transactionCreateRequest.setAccountNumber(savingsAccount.getAccountNumber());
            transactionCreateRequest.setAmount(operationRequest.getAmount());
            transactionCreateRequest.setPreviousBalance(previousBalance);
            transactionCreateRequest.setCurrentBalance(savingsAccount.getBalance());
            transactionCreateRequest.setTransactionType(TransactionType.WITHDRAW);

            ResponseModel<Transaction> response = transactionService.save(transactionCreateRequest);
            if (!response.getSuccess()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Transaction was not created");
                return responseModel;
            }

            savingsAccount.getTransactions().add(response.getData());
            savingsAccount = accountRepository.save(savingsAccount);

            responseModel.setSuccess(true);
            responseModel.setData(savingsAccount);
            responseModel.setMessage("Withdraw successfull");
            return responseModel;
        } catch (Exception e) {
            responseModel.setSuccess(false);
            responseModel.setMessage("Withdraw failed");
            return responseModel;
        }

    }

    public ResponseModel<Account> deposit(OperationRequest operationRequest) {
        Account savings;
        ResponseModel<Account> responseModel = new ResponseModel<>();
        try {
            Optional<Account> savingsAccountOptional = accountRepository.findByAccountNumber(operationRequest.getAccountNum());
            if (!savingsAccountOptional.isPresent()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Account doesn't exist");
                return responseModel;
            }
            savings = savingsAccountOptional.get();
            BigDecimal previousBalance = savings.getBalance();
            savings.setBalance(previousBalance.add(operationRequest.getAmount()));
            savings = accountRepository.save(savings);

            TransactionCreateRequest transactionCreateRequest = new TransactionCreateRequest();
            transactionCreateRequest.setAccountNumber(savings.getAccountNumber());
            transactionCreateRequest.setAmount(operationRequest.getAmount());
            transactionCreateRequest.setPreviousBalance(previousBalance);
            transactionCreateRequest.setCurrentBalance(savings.getBalance());
            transactionCreateRequest.setTransactionType(TransactionType.DEPOSIT);

            ResponseModel<Transaction> response = transactionService.save(transactionCreateRequest);
            if (!response.getSuccess()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Transaction was not created");
                return responseModel;
            }
            savings.getTransactions().add(response.getData());
            savings = accountRepository.save(savings);

        } catch (Exception e) {
            responseModel.setSuccess(false);
            responseModel.setMessage("Deposit failed");
            return responseModel;
        }
        responseModel.setSuccess(true);
        responseModel.setData(savings);
        responseModel.setMessage("Deposit successfull");
        return responseModel;


    }
}
