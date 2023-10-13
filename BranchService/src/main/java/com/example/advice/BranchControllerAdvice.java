package com.example.advice;

import com.example.dto.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
@ControllerAdvice
public class BranchControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleExceptions(Exception e) {
        return ResponseEntity.badRequest().body(new Message(e.getLocalizedMessage()));
    }
}
