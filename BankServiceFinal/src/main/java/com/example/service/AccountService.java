package com.example.service;

import com.example.domain.Account;
import com.example.dto.AccountDto;

public interface AccountService<D extends AccountDto, E extends Account> {

    /**
     * converts the entity given as parameter to the correspending STO
     */
    D get(Long id);

    /**
     * Saves DTO in database
     */
    D save(D dto);

    /**
     * Updates and entity from the corresponding DTO
     */
    D update(E entity, D dto);

    /**
     * Deletes entity from databse
     */
    void delete(E entity);
}
