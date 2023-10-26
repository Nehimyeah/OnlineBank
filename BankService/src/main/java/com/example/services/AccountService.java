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
import com.example.entity.Transaction;
import com.example.enums.AccountStatus;
import com.example.enums.AccountType;
import com.example.enums.Role;
import com.example.enums.TransactionType;
import com.example.repository.AccountRepository;
import com.example.utils.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final CheckingAccountService checkingAccountService;
    private final LoanAccountService loanAccountService;
    private final SavingsAccountService savingsAccountService;
    private final RestTemplate restTemplate;
    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<?> create(AccountRequest accountRequest, String token) {

        User loggedInUser = Util.getPrincipal(token);
        long userId = loggedInUser.getId();
        if (!Role.CUSTOMER.equals(loggedInUser.getRole()))
            throw new RuntimeException("No sufficient Access for this operation");

        return switch (accountRequest.getAccountType()) {
            case "checking" -> checkingAccountService.create(accountRequest, userId);
            case "loan" -> loanAccountService.create(accountRequest, userId);
            case "savings" -> savingsAccountService.create(accountRequest, userId);
            default -> ResponseEntity.badRequest().body("Account type is not correct ");
        };
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
            } else if(Role.MANAGER.equals(loggedInUser.getRole())) {
                BranchDto branchDto = getBranchByManagerId(id,token);
                List<Account> accounts = accountRepository
                        .findByBranchId(branchDto.getBranchId());
                list = convertToAccountResponseList(accounts);
            } else if (Role.ADMIN.equals(loggedInUser.getRole())) {
                List<Account> accounts = accountRepository.findAll();
                list = convertToAccountResponseList(accounts);
            }else{
                throw new RuntimeException("User has no permission");
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.info("exception: " + e.getMessage());
            return new ResponseEntity<>("Error in getting list of accounts" + e, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> accountStatus(String accountNumber, StatusRequest statusRequest, String token) {
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
            accountResponse.setBranchId(account.getBranchId());
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

    public BranchDto getBranchByManagerId(Long id,String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",token);
        //headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<?> response = restTemplate.exchange("http://localhost:3300/branches/get/"+id,HttpMethod.GET,httpEntity, new ParameterizedTypeReference<>() {});
        response.getBody();
        if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND) || response.getStatusCode().isSameCodeAs(HttpStatus.INTERNAL_SERVER_ERROR)){
            throw new RuntimeException("Couldnt get the branch object");
        }

        return modelMapper.map(response.getBody(),BranchDto.class);
    }

    public ResponseEntity<?> getAllAccountByBranch(Long branchId, String token) {
        try {
            User loggedInUser = Util.getPrincipal(token);

            if (!Role.MANAGER.equals(loggedInUser.getRole()) && !Role.ADMIN.equals(loggedInUser.getRole())) {
                return new ResponseEntity<>("No sufficient access for this operation", HttpStatus.FORBIDDEN);
            }

            List<Account> branchAccounts = accountRepository.findByBranchId(branchId);

            if (branchAccounts.isEmpty()) {
                return new ResponseEntity<>("No accounts in the specified branch", HttpStatus.NOT_FOUND);
            } else {
                BigDecimal total = BigDecimal.ZERO;
                List<AccountResponse> list = convertToAccountResponseList(branchAccounts);

                for (Account account : branchAccounts) {
                    for (Transaction transaction : account.getTransactions()) {
                        if (transaction.getTransactionType() == TransactionType.WITHDRAW) {
                            total = total.subtract(transaction.getAmount());
                        }
                        else if(transaction.getTransactionType() == TransactionType.DEPOSIT){
                            total = total.add(transaction.getAmount());
                        }

                        else if(transaction.getTransactionType() == TransactionType.SEND){

                            total = total.subtract(transaction.getAmount());
                        }

                        else if(transaction.getTransactionType() == TransactionType.RECEIVE){
                            total = total.add(transaction.getAmount());
                        }
                    }
                }

                // Create a result object to store the total
                Map<String, Object> result = new HashMap<>();
                result.put("accountList", list);
                result.put("total", total);

                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            log.info("Exception: " + e.getMessage());
            return new ResponseEntity<>("Error in getting list of accounts" + e, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getLoansByBranch(Long branchId, String token) {
        try {
            User loggedInUser = Util.getPrincipal(token);

            if (!Role.MANAGER.equals(loggedInUser.getRole())) {
                return new ResponseEntity<>("No sufficient access for this operation", HttpStatus.FORBIDDEN);
            }

            List<Account> branchAccounts = accountRepository.findByBranchId(branchId);

            if (branchAccounts.isEmpty()) {
                return new ResponseEntity<>("No accounts in the specified branch", HttpStatus.NOT_FOUND);
            }

            BigDecimal total = BigDecimal.ZERO;
            AccountDTO accountDTO = new AccountDTO();
            List<AccountResponse> accountResponseList = new ArrayList<>();

            for (Account account : branchAccounts) {
                if (account instanceof LoanAccount) {
                    AccountResponse accountResponse = getAccountResponse(account);

                    for (Transaction transactionService : account.getTransactions()) {

                        if(transactionService.getTransactionType().equals(TransactionType.WITHDRAW)){

                            total = total.subtract(transactionService.getAmount());
                        }

                        else if(transactionService.getTransactionType().equals(TransactionType.DEPOSIT)){
                            total = total.add(transactionService.getAmount());
                        }

                    }

                    accountResponseList.add(accountResponse);
                }
            }

            accountDTO.setLoanList(accountResponseList);
            accountDTO.setTotal(total);

            return ResponseEntity.ok(accountDTO);

        } catch (Exception e) {
            log.error("Exception: " + e.getMessage());
            return new ResponseEntity<>("Error in getting list of accounts", HttpStatus.BAD_REQUEST);
        }
    }

    private static AccountResponse getAccountResponse(Account account) {
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setAccountType("loan");
        accountResponse.setBranchId(account.getBranchId());
        accountResponse.setAccountNumber(account.getAccountNumber());
        accountResponse.setInterestRate(((LoanAccount) account).getAnnualRate());
        accountResponse.setBalance(account.getBalance());
        accountResponse.setAccountStatus(String.valueOf(account.getAccountStatus()));
        return accountResponse;
    }


}
