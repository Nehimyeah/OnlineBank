package com.example.repository;


import com.example.domain.AnnualAPR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnualAPRRepository extends JpaRepository<AnnualAPR,Long> {
}
