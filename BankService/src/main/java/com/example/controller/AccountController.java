package com.example.controller;


import com.example.dto.ResponseModel;
import com.example.dto.StatusRequest;
import com.example.dto.account.AccountResponse;
import com.example.dto.account.AccountTransferRequest;
import com.example.dto.request.OperationRequest;
import com.example.dto.account.AccountRequest;
import com.example.dto.account.AccountUpdateRequest;
import com.example.enums.AccountStatus;
import com.example.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@CrossOrigin(
        origins = "http://127.0.0.1:5173/",
        allowedHeaders = "*", maxAge = 3600,
        allowCredentials = "true"
)
public class AccountController {

    final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AccountRequest accountRequest,@RequestHeader("Authorization") String token){
        return accountService.create(accountRequest,token);
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody AccountUpdateRequest accountUpdateRequest,@RequestHeader("Authorization") String token){
        return accountService.update(accountUpdateRequest,token);
    }
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody OperationRequest operationRequest,@RequestHeader("Authorization") String token){
        return accountService.withdraw(operationRequest,token);
    }
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody OperationRequest operationRequest,@RequestHeader("Authorization") String token){
        return accountService.deposit(operationRequest,token);
    }
    @PostMapping("/transfer")
    public ResponseEntity<?> transferMoney(@RequestBody AccountTransferRequest accountTransferRequest, @RequestHeader("Authorization") String token){
        return accountService.transferMoney(accountTransferRequest,token);
    }
    @GetMapping("/{accnum}")
    public ResponseEntity<AccountResponse> getAccountByAccNumber(@PathVariable String accnum, @RequestHeader("Authorization") String token){
        return accountService.getAccountByAccNumber(accnum,token);
    }
    @GetMapping("/list")
    public ResponseEntity<?> getList(@RequestHeader("Authorization") String token){
        return accountService.getList(token);
    }
    @PutMapping("/status/{accountNumber}")
    public ResponseEntity<?> accountStatus(@PathVariable String accountNumber, @RequestBody StatusRequest statusRequest, @RequestHeader("Authorization") String token){
        return accountService.accountStatus(accountNumber,statusRequest, token);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> getBranchInfo(@PathVariable Long id, @RequestHeader("Authorization") String token){

        return accountService.getAllAccountByBranch(id, token);
    }

    @GetMapping("/loan/{id}")
    public ResponseEntity<?> getLoanByBranch(@PathVariable Long id, @RequestHeader("Authorization") String token){

        return accountService.getLoansByBranch(id, token);
    }

}
