package com.example.controller;

import com.example.dto.request.account.AccountRequest;
import com.example.dto.request.loan.LoanCreateRequest;
import com.example.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<?> createCheckingAccount(@RequestBody AccountRequest accountRequest){
        accountService.create(accountRequest);
        return ResponseEntity.ok().build();

    }

}
