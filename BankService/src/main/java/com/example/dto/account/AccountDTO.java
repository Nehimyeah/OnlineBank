package com.example.dto.account;

import com.example.entity.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {

    private BigDecimal total;
    private List<AccountResponse> loanList;

}
