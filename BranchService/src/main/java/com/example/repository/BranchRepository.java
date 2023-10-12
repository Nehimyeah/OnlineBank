package com.example.repository;

import com.example.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    @Query("Select b from Branch b where b.branchId = :id")
    public Optional<Branch> findById(@Param("id") Long id);

    @Query("Select b from Branch b where b.branchManagerId = :id")
    public Optional<Branch> findByBranchManagerId(@Param("id") Long id);

}
