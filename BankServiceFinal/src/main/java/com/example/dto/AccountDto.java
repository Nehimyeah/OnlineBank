package com.example.dto;

import com.example.domain.Transaction;
import com.example.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
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
        property = "type")

@JsonSubTypes({
        @JsonSubTypes.Type(value = SavingsAccountDto.class, name = "saving"),
        @JsonSubTypes.Type(value = CheckingAccountDto.class, name = "checking"),
        @JsonSubTypes.Type(value = LoanAccountDto.class, name = "loan")
})

public class AccountDto implements Serializable {

    private UUID id;
    private long accountNumber;
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
