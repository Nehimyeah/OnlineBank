package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankBranchItemDTO {

    private Long branchId;
    private String accountNumber;
    private String accountStatus;
    private BigDecimal balance;
    private Double interestRate;
    private String accountType;
}
