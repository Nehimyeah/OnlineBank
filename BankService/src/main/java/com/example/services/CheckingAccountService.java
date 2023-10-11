package com.example.services;

import com.example.dto.request.CheckingAccountRequest;
import com.example.entity.Account;
import com.example.entity.CheckingAccount;
import com.example.enums.AccountStatus;
import com.example.repository.CheckingAccountRepository;
import com.example.utils.Util;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class CheckingAccountService {


    @Autowired
    CheckingAccountRepository checkingAccountRepository;

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
}
