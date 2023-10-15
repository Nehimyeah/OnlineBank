package com.example.service;

import com.example.domain.SavingsAccount;
import com.example.dto.SavingsAccountDto;

import java.util.List;

public class SavingsService implements AccountService<SavingsAccountDto, SavingsAccount> {

    @Override
    public SavingsAccount get(Long id) {
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
