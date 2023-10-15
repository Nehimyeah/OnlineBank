package com.example.controller;


import com.example.dto.ResponseModel;
import com.example.dto.request.OperationRequest;
import com.example.dto.account.AccountRequest;
import com.example.dto.account.AccountUpdateRequest;
import com.example.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AccountController {

    final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<?> createCheckingAccount(@RequestBody AccountRequest accountRequest,@RequestHeader("Authorization") String token){
        return accountService.create(accountRequest,token);
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody AccountUpdateRequest accountUpdateRequest,@RequestHeader("Authorization") String token){
        return accountService.update(accountUpdateRequest,token);
    }
    @PostMapping("/withdraw")
    public ResponseModel<?> withdraw(@RequestBody OperationRequest operationRequest,@RequestHeader("Authorization") String token){
        return accountService.withdraw(operationRequest,token);
    }
    @PostMapping("/deposit")
    public ResponseModel<?> deposit(@RequestBody OperationRequest operationRequest,@RequestHeader("Authorization") String token){
        return accountService.deposit(operationRequest,token);
    }
    @GetMapping("/{accnum}")
    public ResponseModel<?> getAccountByAccNumber(@PathVariable String accnum){
        return accountService.getAccountByAccNumber(accnum);
    }
    @GetMapping("/list{id}")
    public ResponseEntity<?> getList(@PathVariable Long id){
        return accountService.getList(id);
    }


}
