package com.example.dto.request;

import com.example.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckingAccountRequest {
    private UUID accountId;
    private Long userId;
    private Long branchId;
    @Column(columnDefinition = "status by default is pending")
    private AccountStatus accountStatus = AccountStatus.PENDING;
    private LocalDateTime createdDate;
    private BigDecimal balance;
    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted = false;
    private LocalDateTime deletedDate;
    private Long createdBy;
    private Long deletedBy;

}
