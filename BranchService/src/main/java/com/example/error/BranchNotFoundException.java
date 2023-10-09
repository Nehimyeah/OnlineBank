package com.example.error;

public class BranchNotFoundException extends RuntimeException {

    private String message;

    public BranchNotFoundException() {
    }

    public BranchNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
