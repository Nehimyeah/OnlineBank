//package com.example.controller.accounts;
//
//import com.example.dto.request.SavingsAccountRequest;
//import com.example.entity.Account;
//import com.example.services.SavingsAccountService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/savings")
//@CrossOrigin(origins = "*")
//public class SavingsAccountController {
//    @Autowired
//    SavingsAccountService savingsAccountService;
//    @PostMapping("/create")
//    public ResponseEntity<?> createCheckingAccount(@RequestBody SavingsAccountRequest savingsAccountRequest){
//        return savingsAccountService.create(savingsAccountRequest);
//
//    }
//    //@PreAuthorize("hasAnyRole('ADMIN')")
//    @PostMapping("/update")
//    public ResponseEntity<?> deleteCheckingAccount(@RequestBody SavingsAccountRequest savingsAccountRequest){
//        return savingsAccountService.update(savingsAccountRequest);
//
//    }
//
//    @GetMapping("/{accountnumber}")
//    public ResponseEntity<?> getAccountById(@PathVariable String accountnumber){
//
//        return ResponseEntity.ok(savingsAccountService.findByAccountNumber(accountnumber));
//    }
//}
