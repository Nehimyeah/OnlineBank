package com.example.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CheckingAccountResponseModel {
    private Long userId;
    private String accountNumber;
    private String accountStatus;
    private BigDecimal balance;
}
