package com.example.domain;

import com.example.annotation.EntityHandler;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity
@ToString
@DiscriminatorValue("savings")
@EntityHandler("savingsService")
public class SavingsAccount extends Account {
    private Double annualAPY;
}
