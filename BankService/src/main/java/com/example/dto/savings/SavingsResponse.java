package com.example.dto.savings;

import com.example.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavingsResponse {

    private String accountNumber;
    private AccountStatus accountStatus;
    private BigDecimal balance;
    private Double annualAPY;
}
