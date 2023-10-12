package com.example.enums;

import lombok.Getter;

@Getter
public enum AccountType {

    LOAN(0, "loan"),
    CHECKING(1, "checking"),
    SAVINGS(2, "savings"),
    ;

    private final long id;
    private final String code;

    AccountType(int id, String code) {
        this.id = id;
        this.code = code;
    }

    public static AccountType getByCode(String searchTransactionTypeByCode) {
        for (AccountType accountType : values()) {
            if (accountType.name().equals(searchTransactionTypeByCode)) {
                return accountType;
            }
        }
        return null;
    }

    public static AccountType getById(long id) {
        for (AccountType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        return null;
    }
}
