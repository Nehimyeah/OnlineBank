package com.example.advice;

import com.example.dto.ResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
@Component
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
            return ResponseEntity.badRequest().body(ResponseModel.builder()
                    .message(e.getLocalizedMessage())
                    .build());
        }

}
