package com.example.dto;

import com.example.domain.CheckingAccount;
import com.example.domain.SavingsAccount;
import com.example.domain.Transaction;
import com.example.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@ToString
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")

@JsonSubTypes({
        @JsonSubTypes.Type(value = AccountDTO.class, name = "saving"),
        @JsonSubTypes.Type(value = AccountDTO.class, name = "checking"),
        @JsonSubTypes.Type(value = AccountDTO.class, name = "loan")
})

public class AccountDTO implements Serializable {

    private UUID id;
    private String accountNumber;
    private AccountStatus accountStatus;
    private BigDecimal balance;
    private Long userId;
    private Long branchId;
    private LocalDateTime createdDate;
    private Boolean isDeleted = false;
    private LocalDateTime deletedDate;
    private Long createdBy;
    private Long deletedBy;
    List<Transaction> transactions = new ArrayList<>();
}
