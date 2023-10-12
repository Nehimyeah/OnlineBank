package com.example.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String city;
    private String state;
    private String street1;
    private String street2;
    private int zip;

}
