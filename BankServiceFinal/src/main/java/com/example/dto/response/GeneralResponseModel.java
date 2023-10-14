package com.example.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneralResponseModel<T> {
    private Boolean success;
    private String message;
    private Exception exception;
    private T data;
}
