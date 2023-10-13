package com.example.enums;

import lombok.Getter;

@Getter
public enum AccountType {

    LOAN(1001, "loan"),
    CHECKING(1002, "checking"),
    SAVINGS(1003, "savings"),
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
//    public static Boolean isValidId(Long id){
//        for(Long i:AccountType.get)
//        return
//    }
}
