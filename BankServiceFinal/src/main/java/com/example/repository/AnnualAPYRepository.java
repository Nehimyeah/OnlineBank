package com.example.repository;

import com.example.domain.AnnualAPY;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnualAPYRepository extends JpaRepository<AnnualAPY,Long>{

}
