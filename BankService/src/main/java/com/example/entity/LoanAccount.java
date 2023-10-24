package com.example.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LoanAccount extends Account{
    private Double annualRate;

}
