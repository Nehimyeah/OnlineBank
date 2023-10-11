package com.example.dto.request;

import com.example.enums.TransactionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TransactionRequest {
    private String accountID;
    private BigDecimal amount;


}
