package com.example.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnnualInterestRequest {
    private Long userId;
    private String rateType;
    private Long year;
    private Double annualInterest;
    private int months;
    private String description;
}
