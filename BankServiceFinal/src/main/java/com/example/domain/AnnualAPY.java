package com.example.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class AnnualAPY {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String rateType;
    private Long year;
    private Double annualAPY;
    private int months;
    private String description;
  //  private Long userId;
}
