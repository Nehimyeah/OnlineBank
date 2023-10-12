package com.example.services;

import com.example.dto.request.LoanAccountRequest;
import com.example.dto.request.SavingsAccountRequest;
import com.example.entity.*;
import com.example.enums.AccountStatus;
import com.example.repository.AnnualAPRRepository;
import com.example.repository.AnnualAPYRepository;
import com.example.repository.LoanAccountRepository;
import com.example.repository.SavingsAccountRepository;
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
    SavingsAccountRepository savingsAccountRepository;
    @Autowired
    AnnualAPYRepository apyRepository;

    public ResponseEntity<?> create(SavingsAccountRequest savingsAccountRequest) {

        try {
            SavingsAccount savingsAccount = new SavingsAccount();

            savingsAccount.setAccountNumber(Util.generateAccountNum());
            savingsAccount.setAccountStatus(AccountStatus.PENDING);
            savingsAccount.setBalance(savingsAccount.getBalance()==null? BigDecimal.ZERO:savingsAccount.getBalance());
            if (savingsAccountRequest.getApyRateId() != null) {
                Optional<AnnualAPY> optionalAnnualAPY = apyRepository.findById(savingsAccountRequest.getApyRateId());
                if (!optionalAnnualAPY.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Annual APY id has not been provided");
                }
                savingsAccount.setAnnualAPY(optionalAnnualAPY.get().getAnnualAPY());
            }
            savingsAccount.setUserId(savingsAccountRequest.getUserId());
            savingsAccount.setBranchId(savingsAccountRequest.getBranchId());
            savingsAccount.setCreatedBy(savingsAccountRequest.getCreatedBy());
            savingsAccount.setCreatedDate(LocalDateTime.now());
            savingsAccount.setIsDeleted(false);
            savingsAccountRepository.save(savingsAccount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account has not been created");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Savings account has been created successfully");
    }
    public ResponseEntity<?> update(SavingsAccountRequest savingsAccountRequest) {
        try {
            Optional<SavingsAccount> optionalSavingsAccount = savingsAccountRepository.findById(savingsAccountRequest.getAccountId());
            if (!optionalSavingsAccount.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account doesn't exist");
            }
            SavingsAccount savingsAccount = optionalSavingsAccount.get();
            savingsAccount.setAccountStatus(savingsAccountRequest.getAccountStatus());
            savingsAccount.setBranchId(savingsAccountRequest.getBranchId());
            savingsAccount.setIsDeleted(savingsAccountRequest.getIsDeleted());
            savingsAccount.setDeletedBy(savingsAccountRequest.getDeletedBy());
            savingsAccount.setDeletedDate(LocalDateTime.now());
            savingsAccountRepository.save(savingsAccount);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account has not been updated");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Savings account has been updated successfully");
    }


}
