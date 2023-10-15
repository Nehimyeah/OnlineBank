package com.example.service;

import com.example.Util.Util;
import com.example.domain.Account;
import com.example.domain.CheckingAccount;
import com.example.dto.AccountDto;
import com.example.dto.CheckingAccountDto;
import com.example.dto.ResponseModel;
import com.example.enums.AccountStatus;
import com.example.error.Error;
import com.example.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("checkingService")
@RequiredArgsConstructor
public class CheckingService implements AccountService<CheckingAccountDto, CheckingAccount>{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper mapper;

        @Override
        public CheckingAccount get(Long id) {

            Optional<Account> accountOptional = accountRepository.findByAccountNumber(id);

            if(accountOptional.isPresent()){

                return mapper.map(accountOptional.get(), CheckingAccount.class);
            }

            throw new EntityNotFoundException("Account does not exist");
        }

    @Override
    public CheckingAccountDto save(CheckingAccountDto dto) {

        Optional<Account> checkingAccount;
        Long accountNum;

        do {
            accountNum = Util.generateAccountNum();
            checkingAccount = accountRepository.findByAccountNumber(accountNum);
        } while (checkingAccount.isPresent());

        dto.setAccountNumber(accountNum);

        accountRepository.save(mapper.map(dto, CheckingAccount.class));

        return mapper.map(dto, CheckingAccountDto.class);

    }

    @Override
        public CheckingAccountDto update(CheckingAccount entity, CheckingAccountDto dto) {

                Optional<Account> checkingAccount = accountRepository.findByAccountNumber(entity.getAccountNumber());

            if (checkingAccount.isPresent()) {
                Account entityStatus = checkingAccount.get();

                if (entityStatus.getAccountStatus() == AccountStatus.ACTIVE) {
                    entityStatus.setAccountStatus(dto.getAccountStatus());
                    entityStatus.setIsDeleted(dto.getIsDeleted());
                }
                else if(entityStatus.getAccountStatus() == AccountStatus.NOT_ACTIVE){
                    entityStatus.setAccountStatus(dto.getAccountStatus());
                    entityStatus.setIsDeleted(dto.getIsDeleted());
                }

                // Save the updated entity to your repository
                accountRepository.save(entityStatus);

                return mapper.map(entityStatus, CheckingAccountDto.class);
            } else {
                // Handle the case when the account is not found
                throw new EntityNotFoundException("Account not found");
            }

        }

    @Override
    public void delete(CheckingAccount entity) {

    }


}
