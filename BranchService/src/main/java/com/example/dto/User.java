package com.example.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {

    private String firstName;
    private String lastName;
    private String role;
    private String email;

}
