package com.example.error;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error extends RuntimeException{

    private String message;

    public Error(String message){

        this.message = message;
    }

}
