package com.example.dto;

import com.example.annotation.EntityHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@EntityHandler("loanService")
public class LoanAccountDto extends AccountDto{
    private Double annualAPR;
}
