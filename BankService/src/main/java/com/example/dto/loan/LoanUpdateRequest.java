package com.example.dto.loan;

import com.example.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanUpdateRequest {
        private UUID accountId;
        private Long userId;
        private Long branchId;
        private AccountStatus accountStatus;
        private LocalDateTime createdDate;
        private Boolean isDeleted;
        private LocalDateTime deletedDate;
        private Long createdBy;
        private Long deletedBy;
        private Long aprRateId;
}
