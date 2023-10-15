package com.example.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class AcountResponseDTO {
    BigDecimal total;
    List<AccountResponse> list;
}
