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
public class LoanRequestDto {

    private Long branchId;
    private String accountNumber;
    private String accountStatus;
    private BigDecimal balance;
    private String accountType;
}
