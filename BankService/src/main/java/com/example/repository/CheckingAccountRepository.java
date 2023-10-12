package com.example.repository;

import com.example.entity.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository

public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, UUID> {
    @Query(value = "select * from checking_account where user_id = :id",nativeQuery = true)
    Optional<CheckingAccount> findByUserId(Long id);

}
