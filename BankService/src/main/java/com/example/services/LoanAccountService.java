package com.example.services;

import com.example.dto.request.CheckingAccountRequest;
import com.example.dto.request.LoanAccountRequest;
import com.example.entity.AnnualAPR;
import com.example.entity.LoanAccount;
import com.example.enums.AccountStatus;
import com.example.repository.AnnualAPRRepository;
import com.example.repository.LoanAccountRepository;
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
    LoanAccountRepository loanAccountRepository;
    @Autowired
    AnnualAPRRepository aprRepository;

    public ResponseEntity<?> create(LoanAccountRequest loanAccountRequest) {

        try {
            LoanAccount loanAccount = new LoanAccount();

            loanAccount.setAccountNumber(Util.generateAccountNum());
            loanAccount.setAccountStatus(AccountStatus.PENDING);
            loanAccount.setBalance(loanAccount.getBalance()==null? BigDecimal.ZERO:loanAccount.getBalance());
            if (loanAccountRequest.getAprRateId() != null) {
                Optional<AnnualAPR> optionalAnnualAPR = aprRepository.findById(loanAccountRequest.getAprRateId());
                if (!optionalAnnualAPR.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Annual APR id has not been provided");
                }
                loanAccount.setAnnualAPR(optionalAnnualAPR.get().getAnnualAPR());
            }
            loanAccount.setUserId(loanAccountRequest.getUserId());
            loanAccount.setBranchId(loanAccountRequest.getBranchId());
            loanAccount.setCreatedBy(loanAccountRequest.getCreatedBy());
            loanAccount.setCreatedDate(LocalDateTime.now());
            loanAccount.setIsDeleted(false);
            loanAccountRepository.save(loanAccount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account has not been created");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Loan account has been created successfully");

    }

    public ResponseEntity<?> update(LoanAccountRequest loanAccountRequest) {
        try {
            Optional<LoanAccount> loanAccountOptional = loanAccountRepository.findById(loanAccountRequest.getAccountId());
            if (!loanAccountOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account doesn't exist");
            }
            LoanAccount loanAccount = loanAccountOptional.get();
            loanAccount.setAccountStatus(loanAccountRequest.getAccountStatus());
            loanAccount.setBranchId(loanAccountRequest.getBranchId());
            loanAccount.setIsDeleted(loanAccountRequest.getIsDeleted());
            loanAccount.setDeletedBy(loanAccountRequest.getDeletedBy());
            loanAccount.setDeletedDate(LocalDateTime.now());
            loanAccountRepository.save(loanAccount);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account has not been updated");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Loan account has been updated successfully");
    }
}
