package com.example.services;

import com.example.dto.ResponseModel;
import com.example.dto.request.OperationRequest;
import com.example.dto.account.AccountRequest;
import com.example.dto.account.AccountUpdateRequest;
import com.example.dto.transaction.TransactionCreateRequest;
import com.example.entity.Account;
import com.example.entity.AnnualAPR;
import com.example.entity.LoanAccount;
import com.example.entity.Transaction;
import com.example.enums.AccountStatus;
import com.example.enums.AccountType;
import com.example.enums.TransactionStatus;
import com.example.enums.TransactionType;
import com.example.repository.AccountRepository;
import com.example.repository.AnnualAPRRepository;
import com.example.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LoanAccountService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AnnualAPRRepository aprRepository;
    @Autowired
    TransactionService transactionService;

    public ResponseEntity<?> create(AccountRequest accountRequest,Long userId) {

        try {
            Account loanAccount = new LoanAccount();
            loanAccount.setAccountNumber(Util.generateAccountNum());
            loanAccount.setAccountStatus(AccountStatus.PENDING);
            loanAccount.setBalance(loanAccount.getBalance()==null? BigDecimal.ZERO:loanAccount.getBalance());
            if (accountRequest.getInterestRateId() != null) {
                Optional<AnnualAPR> optionalAnnualAPR = aprRepository.findById(accountRequest.getInterestRateId());
                if (!optionalAnnualAPR.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Annual APR id has not been provided");
                }
                ((LoanAccount) loanAccount).setAnnualRate(optionalAnnualAPR.get().getAnnualAPR());
            }
            loanAccount.setUserId(userId);
            loanAccount.setBranchId(accountRequest.getBranchId());
            loanAccount.setCreatedDate(LocalDateTime.now());
            loanAccount.setIsDeleted(false);
            accountRepository.save(loanAccount);
            return ResponseEntity.status(HttpStatus.OK).body("Loan account has been created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Loan account creation failed");
        }
    }

    public ResponseEntity<?> update(AccountUpdateRequest accountUpdateRequest) {
        try {
            Optional<Account> loanAccountOptional = accountRepository.findByAccountNumber(accountUpdateRequest.getAccnumber());
            if (!loanAccountOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account doesn't exist");
            }
            Account loanAccount = loanAccountOptional.get();
            loanAccount.setAccountStatus(accountUpdateRequest.getAccountStatus());
            loanAccount.setDeletedDate(LocalDateTime.now());
            if (accountUpdateRequest.getInterestRateId() != null) {
                Optional<AnnualAPR> optionalAnnualAPR = aprRepository.findById(accountUpdateRequest.getInterestRateId());
                if (!optionalAnnualAPR.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Annual APR id has not been provided");
                }
                ((LoanAccount) loanAccount).setAnnualRate(optionalAnnualAPR.get().getAnnualAPR());
            }
            accountRepository.save(loanAccount);
            return ResponseEntity.status(HttpStatus.OK).body("Loan account has been updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account has not been updated");
        }

    }
    public ResponseEntity<?> withdraw(OperationRequest operationRequest) {
        try {
            Account loanAccount;
            Optional<Account> loanAccountOptional = accountRepository.findByAccountNumber(operationRequest.getAccountNum());
            if (!loanAccountOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            loanAccount = loanAccountOptional.get();

            // validate account
            if (!Util.validate(loanAccount, operationRequest.getAmount())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Balance is not sufficient");
            }
            BigDecimal previousBalance = loanAccount.getBalance();
            loanAccount.setBalance(previousBalance.subtract(operationRequest.getAmount()));
            loanAccount = accountRepository.save(loanAccount);
            TransactionCreateRequest transactionCreateRequest = new TransactionCreateRequest();
            transactionCreateRequest.setAccountNumber(loanAccount.getAccountNumber());
            transactionCreateRequest.setAmount(operationRequest.getAmount());
            transactionCreateRequest.setPreviousBalance(previousBalance);
            transactionCreateRequest.setTransactionType(TransactionType.WITHDRAW);
            ResponseModel<Transaction> response = transactionService.save(transactionCreateRequest);
            if (!response.getSuccess()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction is not created.");
            }

            loanAccount.getTransactions().add(response.getData());
            accountRepository.save(loanAccount);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    public ResponseEntity<?> deposit(OperationRequest operationRequest) {
        Account loanAccount;
        try {
            Optional<Account> loanAccountOptional = accountRepository.findByAccountNumber(operationRequest.getAccountNum());
            if (!loanAccountOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            loanAccount = loanAccountOptional.get();
            BigDecimal previousBalance = loanAccount.getBalance();
            loanAccount.setBalance(previousBalance.add(operationRequest.getAmount()));
            loanAccount = accountRepository.save(loanAccount);
            TransactionCreateRequest transactionCreateRequest = new TransactionCreateRequest();
            transactionCreateRequest.setAccountNumber(loanAccount.getAccountNumber());
            transactionCreateRequest.setAmount(operationRequest.getAmount());
            transactionCreateRequest.setPreviousBalance(previousBalance);
            transactionCreateRequest.setCurrentBalance(loanAccount.getBalance());
            transactionCreateRequest.setAccountType(AccountType.LOAN);
            transactionCreateRequest.setTransactionType(TransactionType.DEPOSIT);
            transactionCreateRequest.setTransactionStatus(TransactionStatus.APPROVED);

            ResponseModel<Transaction> response = transactionService.save(transactionCreateRequest);
            if (!response.getSuccess()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction is not created.");
            }
            loanAccount.getTransactions().add(response.getData());
            accountRepository.save(loanAccount);
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

}
