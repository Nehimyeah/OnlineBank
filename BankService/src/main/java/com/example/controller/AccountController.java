package com.example.controller;


import com.example.dto.ResponseModel;
import com.example.dto.request.OperationRequest;
import com.example.dto.request.account.AccountRequest;
import com.example.entity.Account;
import com.example.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AccountController {

    final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<?> createCheckingAccount(@RequestBody AccountRequest accountRequest){
        return accountService.create(accountRequest);
    }
    @PutMapping("/update/{accnum}")
    public ResponseEntity<?> update(@PathVariable String accnum, @RequestBody AccountRequest accountRequest){
        return accountService.create(accountRequest);
    }
    @PostMapping("/withdraw")
    public ResponseModel<?> withdraw(@RequestBody OperationRequest operationRequest){
        return accountService.withdraw(operationRequest);
    }
    @PostMapping("/deposit")
    public ResponseModel<?>deposit(@RequestBody OperationRequest operationRequest){
        return accountService.deposit(operationRequest);
    }
    @GetMapping("/{accnum}")
    public ResponseModel<?>deposit(@PathVariable String accnum){
        return accountService.getAccountById(accnum);
    }
    @GetMapping("/list{id}")
    public List<Account> getList(@PathVariable Long id){
        return accountService.getList(id);
    }


}
