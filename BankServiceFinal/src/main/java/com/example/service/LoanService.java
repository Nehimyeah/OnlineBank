package com.example.service;

import com.example.domain.Account;
import com.example.domain.LoanAccount;
import com.example.dto.LoanAccountDto;

import java.util.List;

public class LoanService implements AccountService<LoanAccountDto, LoanAccount> {
//    @Override
//    public LoanAccountDto get(LoanAccount entity) {
//        return null;
//    }

    @Override
    public LoanAccount get(Long id) {
        return null;
    }

    @Override
    public LoanAccountDto save(LoanAccountDto dto) {
        return null;
    }

    @Override
    public LoanAccountDto update(LoanAccount entity, LoanAccountDto dto) {
        return null;
    }

    @Override
    public void delete(LoanAccount entity) {

    }

}
