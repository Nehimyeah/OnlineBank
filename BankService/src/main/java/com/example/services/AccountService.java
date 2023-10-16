package com.example.services;
import com.example.dto.BranchDto;
import com.example.dto.ResponseModel;
import com.example.dto.StatusRequest;
import com.example.dto.account.AcountResponseDTO;
import com.example.dto.request.OperationRequest;
import com.example.dto.User;
import com.example.dto.account.AccountRequest;
import com.example.dto.account.AccountResponse;
import com.example.dto.account.AccountUpdateRequest;
import com.example.entity.Account;
import com.example.enums.AccountStatus;
import com.example.enums.Role;
import com.example.repository.AccountRepository;
import com.example.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService{
    private final AccountRepository accountRepository;
    private final CheckingAccountService checkingAccountService;
    private final LoanAccountService loanAccountService;
    private final SavingsAccountService savingsAccountService;
    private final RestTemplate restTemplate;

    public ResponseEntity<?> create(AccountRequest accountRequest, String token) {

            User loggedInUser = Util.getPrincipal(token);
            long userId = loggedInUser.getId();
            accountRequest.setUserId(userId);

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
    public ResponseEntity<?> update(AccountUpdateRequest accountUpdateRequest,String token) {

        User loggedInUser = Util.getPrincipal(token);
        long userId = loggedInUser.getId();
        //accountRequest.setUserId(userId);
        if (!Role.ADMIN.equals(loggedInUser.getRole()) && !Role.MANAGER.equals(loggedInUser.getRole()))
            throw new RuntimeException("No sufficient Access for this operation");


        switch (accountUpdateRequest.getAccountType()){
            case "checking" :
                return checkingAccountService.update(accountUpdateRequest);
            case "loan":
                return loanAccountService.update(accountUpdateRequest);
            case "savings":
                return savingsAccountService.update(accountUpdateRequest);
        }
        return ResponseEntity.badRequest().body("Account type is not correct ");

    }

    public ResponseModel<?> withdraw(OperationRequest operationRequest,String token) {
        User loggedInUser = Util.getPrincipal(token);
        if (!Role.CUSTOMER.equals(loggedInUser.getRole()))
            throw new RuntimeException("No sufficient Access for this operation");

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

    public ResponseModel<?> deposit(OperationRequest operationRequest,String token) {
        User loggedInUser = Util.getPrincipal(token);
        if (!Role.CUSTOMER.equals(loggedInUser.getRole()))
            throw new RuntimeException("No sufficient Access for this operation");

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

    public ResponseModel<Account> getAccountByAccNumber(String accountNumber,String token) {
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

    public ResponseEntity<?> getList(String token) {
        try {
            User loggedInUser = Util.getPrincipal(token);
            long id = loggedInUser.getId();

            List<Account> accounts = accountRepository.findByUserId(id);
            List<AccountResponse> list = new ArrayList<>();
            BigDecimal sum =BigDecimal.ZERO;
            for (Account account : accounts) {
                AccountResponse accountResponse = new AccountResponse();
                accountResponse.setAccountStatus(String.valueOf(account.getAccountStatus()));
                accountResponse.setAccountNumber(account.getAccountNumber());
                accountResponse.setBalance(account.getBalance());
                list.add(accountResponse);
                sum.add(account.getBalance());
    //            accountResponse.setBranchId(account.getBranchId());
            }
            AcountResponseDTO  acountResponseDTO = new AcountResponseDTO();
            acountResponseDTO.setList(list);
            acountResponseDTO.setTotal(sum);
            return ResponseEntity.ok(acountResponseDTO);
        }catch (Exception e){
            log.info("exception: " + e.getMessage());
            return new ResponseEntity<>("Error in getting list of accounts",HttpStatus.BAD_REQUEST);
        }
    }
    public BranchDto getBranch(Long id){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Long> httpEntity = new HttpEntity<>(id, headers);

        BranchDto branchDto = restTemplate.postForObject("http://localhost:8081/branch",httpEntity, BranchDto.class);
        return branchDto;
    }


    private void authenticateUser(String token) {
        User loggedInUser = Util.getPrincipal(token);
        if (!Role.CUSTOMER.equals(loggedInUser.getRole()))
            throw new RuntimeException("No sufficient Access for this operation");
    }

    public ResponseEntity<?> approveAccount(String accountNumber, StatusRequest statusRequest, String token) {
        try{
        Optional<Account> accountOptional = accountRepository.findByAccountNumber(accountNumber);
        if (!accountOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Account has not been found!");
        }
        Account account = accountOptional.get();
        account.setAccountStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);

        return ResponseEntity.badRequest().body("Account has been updated");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Account has been updated");
        }

    }

}
