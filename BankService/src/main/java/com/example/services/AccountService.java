package com.example.services;
import com.example.dto.ResponseModel;
import com.example.dto.request.OperationRequest;
import com.example.dto.request.account.AccountRequest;
import com.example.dto.request.account.AccountUpdateRequest;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AccountService{
    private final AccountRepository accountRepository;
    private final CheckingAccountService checkingAccountService;
    private final LoanAccountService loanAccountService;
    private final SavingsAccountService savingsAccountService;

    public ResponseEntity<?> create(AccountRequest accountRequest) {
        switch (accountRequest.getAccountType()){
            case "checking" :
                return checkingAccountService.create(accountRequest);
            case "loan":
                return loanAccountService.create(accountRequest);
            case "savings":
                return savingsAccountService.create(accountRequest);
        }
        return ResponseEntity.badRequest().body("Account type is not correct ");

    }
    public ResponseEntity<?> update(String accountNum, AccountUpdateRequest accountUpdateRequest) {
        switch (accountUpdateRequest.getAccountType()){
            case "checking" :
                return checkingAccountService.update(accountNum, accountUpdateRequest);
            case "loan":
                return loanAccountService.update(accountNum, accountUpdateRequest);
            case "savings":
                return savingsAccountService.update(accountNum, accountUpdateRequest);
        }
        return ResponseEntity.badRequest().body("Account type is not correct ");

    }

    public ResponseModel<?> withdraw(OperationRequest operationRequest) {
        switch (operationRequest.getAccountType()){
            case "checking" :
                return checkingAccountService.withdraw(operationRequest);
            case "loan":
                return loanAccountService.withdraw(operationRequest);
            case "savings":
                return savingsAccountService.withdraw(operationRequest);
        }
        ResponseModel<?> responseModel = new ResponseModel<>();
        responseModel.setSuccess(false);
        responseModel.setMessage("Account type is not correct");
        return responseModel;

    }

    public ResponseModel<?> deposit(OperationRequest operationRequest) {
        switch (operationRequest.getAccountType()){
            case "checking" :
                return checkingAccountService.deposit(operationRequest);
            case "loan":
                return loanAccountService.deposit(operationRequest);
            case "savings":
                return savingsAccountService.deposit(operationRequest);
        }
        ResponseModel<?> responseModel = new ResponseModel<>();
        responseModel.setSuccess(false);
        responseModel.setMessage("Account type is not correct");
        return responseModel;
    }

    public ResponseModel<Account> getAccountById(String accountNumber) {
        ResponseModel<Account> responseModel = new ResponseModel<>();
        Optional<Account> accountOptional = accountRepository.findByAccountNumber(accountNumber);
        if (!accountOptional.isPresent()) {
            responseModel.setMessage("Account doesn't exist");
            responseModel.setSuccess(false);
            return responseModel;
        }
        Account account = accountOptional.get();
        responseModel.setSuccess(true);
        responseModel.setData(account);
        return responseModel;
    }

    public List<Account> getList(Long id) {
        return accountRepository.findByUserId(id);


    }
}
