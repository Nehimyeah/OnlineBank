package com.example.dto.account;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AccountResponse {

    private String accountNumber;
    private String accountStatus;
    private BigDecimal balance;
//    private Long branchId;

}
