package com.example.services;

import com.example.dto.request.account.AccountRequest;
import com.example.entity.*;
import com.example.enums.AccountStatus;
import com.example.enums.AccountType;
import com.example.repository.AccountRepository;
import com.example.utils.Util;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService{
    private final AccountRepository accountRepository;
    private final AnnualAPYService apyService;
    private final AnnualAPRService aprService;

    private final ModelMapper modelMapper;
/**
    public void create(AccountRequest accountRequest) {
        switch(accountRequest.getAccountType()){
            case "saving":
                SavingsAccount account = modelMapper.map(accountRequest, SavingsAccount.class);
                SavingsAccount sAccount = account;
                sAccount.setAnnualAPY(apyService.findById(accountRequest.getInterestRateId()));
                accountRepository.save(sAccount);
                break;
            case "loan":
                LoanAccount lAccount = (LoanAccount) account;
                lAccount.setAnnualAPR(aprService.findById(accountRequest.getInterestRateId()));
                accountRepository.save(lAccount);
                break;
            case "checking":
                accountRepository.save(account);


            account.setAccountNumber(Util.generateAccountNum());
            account.setAccountStatus(AccountStatus.PENDING);
            account.setBalance(account.getBalance() == null ? BigDecimal.ZERO : account.getBalance());
//            checkingAccount.setUserId(accountRequest.getUserId());
//            checkingAccount.setBranchId(accountRequest.getBranchId());
//            checkingAccount.setCreatedBy(accountRequest.getCreatedBy());
            account.setCreatedDate(LocalDateTime.now());
            account.setIsDeleted(false);



        }
        accountRepository.save(account);
//        = modelMapper.map(accountRequest, Account.class);
//        if(AccountType.getByCode(accountRequest.getAccountType()))

        accountRepository.save(account);
        System.out.println(account);

    }



    void a() {
        if (savingsAccountRequest.getApyRateId() != null) {
            Optional<AnnualAPY> optionalAnnualAPY = apyRepository.findById(savingsAccountRequest.getApyRateId());
            if (!optionalAnnualAPY.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Annual APY id has not been provided");
            }
            ((SavingsAccount) savingsAccount).setAnnualAPY(optionalAnnualAPY.get().getAnnualAPY());
        }
    }  */
}
