package com.example.repository;

import com.example.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {


    @Query("Select a from Account a where a.accountNumber = :accountNumber")
    public Optional<Account> findByAccountNumber(@Param("accountNumber") Long accountNumber);
}
