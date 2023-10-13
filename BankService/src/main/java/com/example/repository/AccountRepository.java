package com.example.repository;

import com.example.entity.Account;
import com.example.entity.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
//    @Query(value = "select * from Account where user_id = :id",nativeQuery = true)
//    Optional<Account> findByUserId(Long id);
}
