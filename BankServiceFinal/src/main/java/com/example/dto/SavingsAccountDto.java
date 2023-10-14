package com.example.dto;

import com.example.annotation.EntityHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@EntityHandler("savingsService")
public class SavingsAccountDto extends AccountDto{
    private Double annualAPY;
}
