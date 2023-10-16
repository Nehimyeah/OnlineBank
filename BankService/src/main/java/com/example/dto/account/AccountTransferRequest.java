package com.example.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountTransferRequest {
    private String fromAccountNum;
    private String toAccountNum;
    private BigDecimal amount;

}
