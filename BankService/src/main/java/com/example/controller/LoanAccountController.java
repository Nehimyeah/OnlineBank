package com.example.controller;

import com.example.dto.request.loan.LoanCreateRequest;
import com.example.dto.request.loan.LoanUpdateRequest;
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
    public ResponseEntity<?> createCheckingAccount(@RequestBody LoanCreateRequest loanCreateRequest){
        return loanAccountService.create(loanCreateRequest);

    }
    //@PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody LoanUpdateRequest loanUpdateRequest){
        return loanAccountService.update(loanUpdateRequest);

    }
}
