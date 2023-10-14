package com.example.domain;

import com.example.annotation.EntityHandler;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@DiscriminatorValue("loan")
@EntityHandler("loanService")
public class LoanAccount extends Account{
    private Double annualAPR;

}
