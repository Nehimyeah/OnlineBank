package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestAccountInfo {

    private Long branchId;
    private String accountNumber;
    private String accountStatus;
    private String accountType;
    private BigDecimal balance;
}
