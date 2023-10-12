package com.example.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;
    private String branchName;
    private Long branchManagerId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Address address;

}
