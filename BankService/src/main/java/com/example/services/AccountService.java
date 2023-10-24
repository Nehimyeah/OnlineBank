package com.example.services;

import com.example.dto.BranchDto;
import com.example.dto.ResponseModel;
import com.example.dto.StatusRequest;
import com.example.dto.account.*;
import com.example.dto.request.OperationRequest;
import com.example.dto.User;
import com.example.entity.Account;
import com.example.entity.LoanAccount;
import com.example.entity.SavingsAccount;
import com.example.enums.AccountStatus;
import com.example.enums.Role;
import com.example.repository.AccountRepository;
import com.example.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final CheckingAccountService checkingAccountService;
    private final LoanAccountService loanAccountService;
    private final SavingsAccountService savingsAccountService;
    private final RestTemplate restTemplate;

    public ResponseEntity<?> create(AccountRequest accountRequest, String token) {

        User loggedInUser = Util.getPrincipal(token);
        long userId = loggedInUser.getId();
        if (!Role.CUSTOMER.equals(loggedInUser.getRole()))
            throw new RuntimeException("No sufficient Access for this operation");

        switch (accountRequest.getAccountType()) {
            case "checking":
                return checkingAccountService.create(accountRequest, userId);
            case "loan":
                return loanAccountService.create(accountRequest, userId);
            case "savings":
                return savingsAccountService.create(accountRequest, userId);
            default:
                return ResponseEntity.badRequest().body("Account type is not correct ");
        }
    }

    public ResponseEntity<?> update(AccountUpdateRequest accountUpdateRequest, String token) {

        User loggedInUser = Util.getPrincipal(token);
        //long userId = loggedInUser.getId();
        if (!Role.ADMIN.equals(loggedInUser.getRole()) && !Role.MANAGER.equals(loggedInUser.getRole()))
            throw new RuntimeException("No sufficient Access for this operation");

        switch (accountUpdateRequest.getAccountType()) {
            case "checking":
                return checkingAccountService.update(accountUpdateRequest);
            case "loan":
                return loanAccountService.update(accountUpdateRequest);
            case "savings":
                return savingsAccountService.update(accountUpdateRequest);
            default:
                return ResponseEntity.badRequest().body("Account type is not correct ");
        }

    }

    public ResponseEntity<?> withdraw(OperationRequest operationRequest, String token) {
        User loggedInUser = Util.getPrincipal(token);
        if (!Role.CUSTOMER.equals(loggedInUser.getRole()))
            throw new RuntimeException("No sufficient Access for this operation");

        switch (operationRequest.getAccountType()) {
            case "checking":
                return checkingAccountService.withdraw(operationRequest);
            case "loan":
                return loanAccountService.withdraw(operationRequest);
            case "savings":
                return savingsAccountService.withdraw(operationRequest);
            default:
                return ResponseEntity.badRequest().body("Account type is not correct");
        }
    }


    public ResponseEntity<?> deposit(OperationRequest operationRequest, String token) {
        User loggedInUser = Util.getPrincipal(token);
        if (!Role.CUSTOMER.equals(loggedInUser.getRole()))
            throw new RuntimeException("No sufficient Access for this operation");

        switch (operationRequest.getAccountType()) {
            case "checking":
                return checkingAccountService.deposit(operationRequest);
            case "loan":
                return loanAccountService.deposit(operationRequest);
            case "savings":
                return savingsAccountService.deposit(operationRequest);
            default:
                return ResponseEntity.badRequest().body("Account type is not correct");

        }

    }

    public ResponseEntity<AccountResponse> getAccountByAccNumber(String accountNumber, String token) {
        ResponseModel<AccountResponse> responseModel = new ResponseModel<>();
        try {
            Optional<Account> accountOptional = accountRepository.findByAccountNumber(accountNumber);
            if (!accountOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Account account = accountOptional.get();
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setAccountNumber(account.getAccountNumber());
            accountResponse.setAccountStatus(String.valueOf(account.getAccountStatus()));
            accountResponse.setBalance(account.getBalance());
            accountResponse.setTransactions(account.getTransactions());
            if (account instanceof LoanAccount) {
                accountResponse.setInterestRate(((LoanAccount) account).getAnnualRate());
                accountResponse.setAccountType("loan");
            } else if (account instanceof SavingsAccount) {
                accountResponse.setInterestRate(((SavingsAccount) account).getAnnualRate());
                accountResponse.setAccountType("savings");
            } else {
                accountResponse.setAccountType("checking");
            }
            return ResponseEntity.status(HttpStatus.OK).body(accountResponse);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<?> getList(String token) {
        try {
            User loggedInUser = Util.getPrincipal(token);
            long id = loggedInUser.getId();
            List<AccountResponse> list;
            if (Role.CUSTOMER.equals(loggedInUser.getRole())) {
                List<Account> accounts = accountRepository.findByUserId(id);
                list = convertToAccountResponseList(accounts);
            } else {
                List<Account> accounts = accountRepository.findAll();
                list = convertToAccountResponseList(accounts);
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.info("exception: " + e.getMessage());
            return new ResponseEntity<>("Error in getting list of accounts" + e, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> approveAccount(String accountNumber, StatusRequest statusRequest, String token) {
        try {
            User loggedInUser = Util.getPrincipal(token);
            if (Role.CUSTOMER.equals(loggedInUser.getRole()))
                throw new RuntimeException("No sufficient Access for this operation");

            Optional<Account> accountOptional = accountRepository.findByAccountNumber(accountNumber);
            if (!accountOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Account account = accountOptional.get();
            //ACTIVE, PENDING, BLOCKED, NOT_ACTIVE

            if(statusRequest.getStatus().equalsIgnoreCase("ACTIVE")){
                account.setAccountStatus(AccountStatus.ACTIVE);
            } else if (statusRequest.getStatus().equalsIgnoreCase("BLOCKED")) {
                account.setAccountStatus(AccountStatus.BLOCKED);
            } else if (statusRequest.getStatus().equalsIgnoreCase("NOT_ACTIVE")) {
                account.setAccountStatus(AccountStatus.NOT_ACTIVE);
            }
            accountRepository.save(account);

            return ResponseEntity.ok().body("Status changed");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

    }

    public ResponseEntity<?> transferMoney(AccountTransferRequest accountTransferRequest, String token) {
        User loggedInUser = Util.getPrincipal(token);
        if (!Role.CUSTOMER.equals(loggedInUser.getRole()))
            throw new RuntimeException("No sufficient Access for this operation");

        return checkingAccountService.transferMoney(accountTransferRequest);

    }

    public List<AccountResponse> convertToAccountResponseList(List<Account> accounts) {
        List<AccountResponse> list = new ArrayList<>();
        for (Account account : accounts) {
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setAccountStatus(String.valueOf(account.getAccountStatus()));
            accountResponse.setAccountNumber(account.getAccountNumber());
            accountResponse.setBalance(account.getBalance());
            if (account instanceof LoanAccount) {
                accountResponse.setInterestRate(((LoanAccount) account).getAnnualRate());
                accountResponse.setAccountType("loan");
            } else if (account instanceof SavingsAccount) {
                accountResponse.setInterestRate(((SavingsAccount) account).getAnnualRate());
                accountResponse.setAccountType("savings");
            } else {
                accountResponse.setAccountType("checking");
            }
            list.add(accountResponse);
        }
        return list;
    }

    private void authenticateUser(String token) {
        User loggedInUser = Util.getPrincipal(token);
        if (!Role.CUSTOMER.equals(loggedInUser.getRole()))
            throw new RuntimeException("No sufficient Access for this operation");
    }

    public BranchDto getBranch(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Long> httpEntity = new HttpEntity<>(id, headers);

        BranchDto branchDto = restTemplate.postForObject("http://localhost:8081/branch", httpEntity, BranchDto.class);
        return branchDto;
    }
}
