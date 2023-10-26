package com.example.enums;

public enum TransactionType {
    DEPOSIT(0, "deposit"),
    WITHDRAW(1, "withdraw"),
    PAYMENTTOLOAN(2, "paymenttoloan"),
    SEND(3, "send"),
    RECEIVE(4, "receive"),
    ;

    private final long id;
    private final String code;

    TransactionType(int id, String code) {
        this.id = id;
        this.code = code;
    }

    public static TransactionType getByCode(String searchTransactionTypeByCode) {
        for (TransactionType transactionType : values()) {
            if (transactionType.name().equals(searchTransactionTypeByCode)) {
                return transactionType;
            }
        }
        return null;
    }

    public static TransactionType getById(long id) {
        for (TransactionType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        return null;
    }
}
