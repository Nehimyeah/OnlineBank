package com.example.services;

import com.example.dto.ResponseModel;
import com.example.dto.request.OperationRequest;
import com.example.dto.request.TransactionRequest;
import com.example.dto.request.loan.LoanCreateRequest;
import com.example.dto.request.loan.LoanUpdateRequest;
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
import java.util.UUID;

@Service
public class LoanAccountService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AnnualAPRRepository aprRepository;
    @Autowired
    TransactionService transactionService;

    public ResponseEntity<?> create(LoanCreateRequest loanCreateRequest) {

        try {
            Account loanAccount = new LoanAccount();
            loanAccount.setAccountNumber(Util.generateAccountNum());
            loanAccount.setAccountStatus(AccountStatus.PENDING);
            loanAccount.setBalance(loanAccount.getBalance()==null? BigDecimal.ZERO:loanAccount.getBalance());
            if (loanCreateRequest.getAprRateId() != null) {
                Optional<AnnualAPR> optionalAnnualAPR = aprRepository.findById(loanCreateRequest.getAprRateId());
                if (!optionalAnnualAPR.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Annual APR id has not been provided");
                }
                ((LoanAccount) loanAccount).setAnnualAPR(optionalAnnualAPR.get().getAnnualAPR());
            }
            loanAccount.setUserId(loanCreateRequest.getUserId());
            loanAccount.setBranchId(loanCreateRequest.getBranchId());
    //        loanAccount.setCreatedBy(loanCreateRequest.getCreatedBy());
            loanAccount.setCreatedDate(LocalDateTime.now());
            loanAccount.setIsDeleted(false);
            accountRepository.save(loanAccount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Loan account creation failed");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Loan account has been created successfully");

    }

    public ResponseEntity<?> update(LoanUpdateRequest loanUpdateRequest) {
        try {
            Optional<Account> loanAccountOptional = accountRepository.findById(loanUpdateRequest.getAccountId());
            if (!loanAccountOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account doesn't exist");
            }
            Account loanAccount = loanAccountOptional.get();
            loanAccount.setAccountStatus(loanUpdateRequest.getAccountStatus());
            loanAccount.setBranchId(loanUpdateRequest.getBranchId());
            loanAccount.setIsDeleted(loanUpdateRequest.getIsDeleted());
        //  loanAccount.setDeletedBy(loanAccountRequest.getDeletedBy());
            loanAccount.setDeletedDate(LocalDateTime.now());
            if (loanUpdateRequest.getAprRateId() != null) {
                Optional<AnnualAPR> optionalAnnualAPR = aprRepository.findById(loanUpdateRequest.getAprRateId());
                if (!optionalAnnualAPR.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Annual APR id has not been provided");
                }
                ((LoanAccount) loanAccount).setAnnualAPR(optionalAnnualAPR.get().getAnnualAPR());
            }
            accountRepository.save(loanAccount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account has not been updated");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Loan account has been updated successfully");
    }
    public ResponseModel<Account> withdraw(OperationRequest operationRequest) {
        ResponseModel<Account> responseModel = new ResponseModel<>();
        try {
            Account loanAccount;
            Optional<Account> loanAccountOptional = accountRepository.findById(UUID.fromString(operationRequest.getAccountId()));
            if (loanAccountOptional.isPresent()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Account doesn't exist");
                return responseModel;
            }

            loanAccount = loanAccountOptional.get();

            // validate account
            if (!Util.validate(loanAccount, operationRequest.getAmount())) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Balance is not sufficient ");
                return responseModel;
            }
            BigDecimal previousBalance = loanAccount.getBalance();
            loanAccount.setBalance(previousBalance.subtract(operationRequest.getAmount()));
            loanAccount = accountRepository.save(loanAccount);
            TransactionRequest transactionRequest = new TransactionRequest();
            transactionRequest.setAccountNumber(loanAccount.getAccountNumber());
            transactionRequest.setAmount(operationRequest.getAmount());
            transactionRequest.setPreviousBalance(previousBalance);
            transactionRequest.setCurrentBalance(loanAccount.getBalance());
            transactionRequest.setAccountType(AccountType.CHECKING);
            transactionRequest.setTransactionStatus(TransactionStatus.APPROVED);
            transactionRequest.setTransactionType(TransactionType.WITHDRAW);
            transactionRequest.setAccount(loanAccount);
            ResponseModel<Transaction> response = transactionService.save(transactionRequest);
            if (!response.getSuccess()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Transaction failed");
                return responseModel;
            }
            loanAccount.getTransactions().add(response.getData());
            loanAccount = accountRepository.save(loanAccount);
            responseModel.setSuccess(true);
            responseModel.setData(loanAccount);
            responseModel.setMessage("Withdraw successfull");
            return responseModel;
        } catch (Exception e) {
            responseModel.setSuccess(false);
            responseModel.setMessage("Withdraw failed");
            return responseModel;
        }

    }

    public ResponseModel<Account> deposit(OperationRequest operationRequest) {
        Account loanAccount;
        ResponseModel<Account> responseModel = new ResponseModel<>();
        try {
            Optional<Account> loanAccountOptional = accountRepository.findById(UUID.fromString(operationRequest.getAccountId()));
            if (!loanAccountOptional.isPresent()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Account doesn't exist");
                return responseModel;
            }
            loanAccount = loanAccountOptional.get();
            BigDecimal previousBalance = loanAccount.getBalance();
            loanAccount.setBalance(previousBalance.add(operationRequest.getAmount()));
            loanAccount = accountRepository.save(loanAccount);
            TransactionRequest transactionRequest = new TransactionRequest();
            transactionRequest.setAccountNumber(loanAccount.getAccountNumber());
            transactionRequest.setAmount(operationRequest.getAmount());
            transactionRequest.setPreviousBalance(previousBalance);
            transactionRequest.setCurrentBalance(loanAccount.getBalance());
            transactionRequest.setAccountType(AccountType.CHECKING);
            transactionRequest.setTransactionType(TransactionType.DEPOSIT);
            transactionRequest.setTransactionStatus(TransactionStatus.APPROVED);
            transactionRequest.setAccount(loanAccount);
            ResponseModel<Transaction> response = transactionService.save(transactionRequest);
            if (!response.getSuccess()) {
                responseModel.setSuccess(false);
                responseModel.setMessage("Transaction was not created");
                return responseModel;
            }
            loanAccount.getTransactions().add(response.getData());
            loanAccount = accountRepository.save(loanAccount);

        } catch (Exception e) {
            responseModel.setSuccess(false);
            responseModel.setMessage("Deposit failed");
            return responseModel;
        }
        responseModel.setSuccess(true);
        responseModel.setData(loanAccount);
        responseModel.setMessage("Deposit succussfull");
        return responseModel;
    }

}
