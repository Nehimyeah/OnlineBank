package com.example.dto.account;

import com.example.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.core.util.Json;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AccountResponse {

    private Long branchId;
    private String accountNumber;
    private String accountStatus;
    private BigDecimal balance;


}
