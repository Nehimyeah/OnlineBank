package com.example.controller;

import com.example.dto.ResponseModel;
import com.example.dto.request.checking.CheckingAccountRequest;
import com.example.dto.request.OperationRequest;
import com.example.entity.Account;
import com.example.services.CheckingAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checking")
@CrossOrigin(origins = "*")
public class CheckingAccountController {
    final
    CheckingAccountService checkingAccountService;

    public CheckingAccountController(CheckingAccountService checkingAccountService) {
        this.checkingAccountService = checkingAccountService;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createCheckingAccount(@RequestBody CheckingAccountRequest checkingAccountRequest){
        return checkingAccountService.create(checkingAccountRequest);
    }
    //@PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<?> deleteCheckingAccount(@RequestBody CheckingAccountRequest checkingAccountRequest){
        return checkingAccountService.update(checkingAccountRequest);
    }
    @PostMapping("/withdraw")
    public ResponseModel<Account> withdraw(@RequestBody OperationRequest operationRequest){
        return checkingAccountService.withdraw(operationRequest);
    }
    @PostMapping("/deposit")
    public ResponseModel<Account>deposit(@RequestBody OperationRequest operationRequest){
        return checkingAccountService.deposit(operationRequest);
    }
//    @GetMapping("/{id}")
//    public ResponseModel<Account> getCheckingAccount(@PathVariable Long id){
//        return checkingAccountService.getCheckingAccount(id);
//    }



}
