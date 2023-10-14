package com.example.dto.request.loan;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanCreateRequest {
    private Long userId;
    private Long branchId;
    private Long aprRateId;
}
