package com.assessment.bank.serverapp.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", ex.getStatusCode().value());
        responseBody.put("message", ex.getReason());

        return new ResponseEntity<>(responseBody, ex.getStatusCode());
    }
}
