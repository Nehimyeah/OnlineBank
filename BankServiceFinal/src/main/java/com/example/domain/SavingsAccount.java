package com.example.domain;

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
public class SavingsAccount extends Account {
    private Double annualAPY;
}
