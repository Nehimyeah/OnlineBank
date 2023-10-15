package com.example.dto.account;

import com.example.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountUpdateRequest {

    private String accnumber;
    private String accountType;
    private AccountStatus accountStatus;
    private Long interestRateId;
}
