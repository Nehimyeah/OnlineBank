package com.example.service;

import com.example.domain.SavingsAccount;
import com.example.dto.SavingsAccountDto;

public class SavingsService implements AccountService<SavingsAccountDto, SavingsAccount> {
//    @Override
//    public SavingsAccountDto get(SavingsAccount entity) {
//        return null;
//    }

    @Override
    public SavingsAccountDto get(Long id) {
        return null;
    }

    @Override
    public SavingsAccountDto save(SavingsAccountDto dto) {
        return null;
    }

    @Override
    public SavingsAccountDto update(SavingsAccount entity, SavingsAccountDto dto) {
        return null;
    }

    @Override
    public void delete(SavingsAccount entity) {

    }
}
