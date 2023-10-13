package com.example.controller;

import com.example.dto.request.SavingsAccountRequest;
import com.example.services.SavingsAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/savings")
@CrossOrigin(origins = "*")
public class SavingsAccountController {
    @Autowired
    SavingsAccountService savingsAccountService;
    @PostMapping("/create")
    public ResponseEntity<?> createCheckingAccount(@RequestBody SavingsAccountRequest savingsAccountRequest){
        return savingsAccountService.create(savingsAccountRequest);

    }
    //@PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<?> deleteCheckingAccount(@RequestBody SavingsAccountRequest savingsAccountRequest){
        return savingsAccountService.update(savingsAccountRequest);

    }
}
