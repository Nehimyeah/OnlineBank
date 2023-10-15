package com.example.dto.checking;

import com.example.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
public class CheckingAccountResponseModel {
    private Long userId;
    private String accountNumber;
    private String accountStatus;
    private BigDecimal balance;
}
