package com.example.repository;

import com.example.entity.Account;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
@Primary
public interface AccountRepository extends JpaRepository<Account, UUID> {
//    @Query(value = "select * from Account where user_id = :id",nativeQuery = true)
//    Optional<Account> findByUserId(Long id);

    @Query("Select a from Account a where a.accountNumber = :accountNumber")
    Optional<Account> findByAccountNumber(@Param("accountNumber") String accountNumber);
    @Query("Select a from Account a where a.userId = :userId")
    List<Account> findByUserId(@Param("userId") Long userId);



}
