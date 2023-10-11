package com.example.controller;

import com.example.dto.request.CheckingAccountRequest;
import com.example.dto.request.LoanAccountRequest;
import com.example.services.LoanAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
@CrossOrigin(origins = "*")
public class LoanAccountController {
    @Autowired
    LoanAccountService loanAccountService;
    @PostMapping("/create")
    public ResponseEntity<?> createCheckingAccount(@RequestBody LoanAccountRequest loanAccountRequest){
        return loanAccountService.create(loanAccountRequest);

    }
    //@PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<?> deleteCheckingAccount(@RequestBody LoanAccountRequest loanAccountRequest){
        return loanAccountService.update(loanAccountRequest);

    }
}
