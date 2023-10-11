package com.example.repository;

import com.example.entity.LoanAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoanAccountRepository extends JpaRepository<LoanAccount, UUID> {

}
