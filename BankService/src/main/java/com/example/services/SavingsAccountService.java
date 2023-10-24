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
                ((SavingsAccount) savingsAccount).setAnnualRate(optionalAnnualAPY.get().getAnnualAPY());
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


    public ResponseEntity<?> withdraw(OperationRequest operationRequest) {
        try {
            Account savingsAccount;
            Optional<Account> checkingAccountOptional = accountRepository.findByAccountNumber(operationRequest.getAccountNum());
            if (!checkingAccountOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            savingsAccount = checkingAccountOptional.get();

            // validate account
            if (!Util.validate(savingsAccount, operationRequest.getAmount())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Balance is not sufficient ");
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction is not created.");
            }
            savingsAccount.getTransactions().add(response.getData());
            accountRepository.save(savingsAccount);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    public ResponseEntity<?> deposit(OperationRequest operationRequest) {
        Account savings;
        try {
            Optional<Account> savingsAccountOptional = accountRepository.findByAccountNumber(operationRequest.getAccountNum());
            if (!savingsAccountOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction is not created.");
            }
            savings.getTransactions().add(response.getData());
            accountRepository.save(savings);
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
