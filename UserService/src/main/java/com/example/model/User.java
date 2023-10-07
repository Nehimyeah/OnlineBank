package com.example.model;

import com.example.constants.Role;
import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private String password;
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isActive;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Address address;
}
