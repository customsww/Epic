package com.example.backend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

    @ResponseStatus(HttpStatus.CONFLICT) // 409
    public class DuplicateResourceException extends RuntimeException {
    
        public DuplicateResourceException(String message) {
            super(message);
        }
    }
    