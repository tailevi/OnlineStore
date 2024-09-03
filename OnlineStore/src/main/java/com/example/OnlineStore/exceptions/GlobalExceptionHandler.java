package com.example.OnlineStore.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductsServiceException.class)
    public ResponseEntity<RuntimeErrorResponse> handlePlayerServiceException(ProductsServiceException ex, HttpServletRequest request) {
        RuntimeErrorResponse runtimeErrorResponse = new RuntimeErrorResponse(
                request.getRequestURI(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(runtimeErrorResponse);
    }
}
