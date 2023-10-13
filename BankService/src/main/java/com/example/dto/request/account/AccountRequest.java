package com.example.dto.request.account;

import com.example.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountRequest {
    private Long userId;
    private Long branchId;
    private String accountType;
    private Long interestRateId;
}
