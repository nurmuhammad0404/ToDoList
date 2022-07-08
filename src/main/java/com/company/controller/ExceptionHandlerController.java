package com.company.controller;

import com.company.exc.ItemNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ExceptionHandlerController {
    @ExceptionHandler({ItemNotFoundException.class})
    public ResponseEntity<?> handleBadRequestException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
