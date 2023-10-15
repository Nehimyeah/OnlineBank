package com.example.dto;

import com.example.enums.Role;
import lombok.*;


@Builder
@AllArgsConstructor
@Getter
@Setter
public class User {

    private long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Role role;


}