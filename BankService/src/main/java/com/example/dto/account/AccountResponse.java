package com.example.dto.account;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AccountResponse {

    private List<RequestAccountInfo> requestAccountInfoList;
    private BigDecimal balance;


}
