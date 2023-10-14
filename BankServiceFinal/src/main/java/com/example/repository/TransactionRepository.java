package com.example.repository;


import com.example.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

//    @Query("SELECT t FROM Transaction t WHERE t.account = :account")
//    List<Transaction> findTransactionsByAccount(@Param("account") Account account);

//    @Query(value = "select * from transaction t where t.account_id= :accountid", nativeQuery = true)
//    List<Transaction> findAllByAccountId(UUID accountId);


}
