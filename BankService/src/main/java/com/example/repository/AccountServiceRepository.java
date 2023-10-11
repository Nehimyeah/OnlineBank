package com.example.repository;

import com.example.entity.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountServiceRepository extends JpaRepository<CheckingAccount, UUID> {
}
