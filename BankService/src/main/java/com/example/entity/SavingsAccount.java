package com.example.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class SavingsAccount extends Account{
    private Double annualAPY;
}
