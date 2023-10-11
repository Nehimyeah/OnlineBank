package com.example.repository;

import com.example.controller.SavingsAccountController;
import com.example.entity.CheckingAccount;
import com.example.entity.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, UUID> {
}
