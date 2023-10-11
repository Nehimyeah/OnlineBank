package com.example.entity;

import com.example.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    private BigDecimal balance;
    private Long userId;
    private Long branchId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted = false;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deletedDate;
    private Long createdBy;
    private Long deletedBy;


}
