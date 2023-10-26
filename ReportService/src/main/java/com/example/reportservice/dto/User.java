package com.example.reportservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class User {

    private String firstName;
    private String lastName;
    private String password;
    private String email;
}


