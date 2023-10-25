package com.example.dto.account;

import com.example.entity.Transaction;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse {

    private Long branchId;
    private String accountNumber;
    private String accountStatus;
    private BigDecimal balance;
    private Double interestRate;
    private String accountType;
    private List<Transaction> transactions;

}
