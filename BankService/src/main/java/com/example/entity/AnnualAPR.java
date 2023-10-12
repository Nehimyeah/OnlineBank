package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AnnualAPR {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String rateType;
    private Long year;
    private Double annualAPR;
    private int months;
    private String description;
  //  private Long userId;

}
