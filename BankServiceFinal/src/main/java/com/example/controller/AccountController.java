package com.example.controller;

import com.example.domain.CheckingAccount;
import com.example.dto.AccountDto;
import com.example.dto.CheckingAccountDto;
import com.example.service.CheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    CheckingService checkingService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CheckingAccountDto accountDTO){
        try {
            checkingService.save(accountDTO);
            return ResponseEntity.ok("Successfully created account");
        } catch (Exception e) {
            // Handle the exception and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create account: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody CheckingAccountDto checkingAccountDto){
//        return checkingService.update(checkingAccountDto);

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){

        return ResponseEntity.ok(checkingService.get(id));
    }

//    @PostMapping("/withdraw")
//    public com.example.dto.ResponseModel<Account> withdraw(@RequestBody OperationRequest operationRequest){
//        return checkingAccountService.withdraw(operationRequest);
//    }
//    @PostMapping("/deposit")
//    public com.example.dto.ResponseModel<Account>deposit(@RequestBody OperationRequest operationRequest){
//        return checkingAccountService.deposit(operationRequest);
//    }

}
