package com.example.repository;

import com.example.entity.Account;
import com.example.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
//    @Query(value = "select * from transaction t where t.account_id= :accountid", nativeQuery = true)
//    List<Transaction> findAllByAccount(String accountId);
    @Query("SELECT t FROM Transaction t WHERE t.account = :account")
    List<Transaction> findTransactionsByAccount(@Param("account") Account account);

}
