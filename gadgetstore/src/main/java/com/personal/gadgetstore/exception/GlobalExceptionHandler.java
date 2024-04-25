package com.personal.gadgetstore.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest()
                .body(errors);
    }

    @ExceptionHandler({ResourceNotFoundException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<String> handleUserNotFoundException(ResourceNotFoundException ex) {
        logger.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Resource not found: The requested resource does not exist.");
    }

    @ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<String> handleInvalidFileTypeException(BadApiRequestException ex) {
        logger.info(ex.getMessage());
        return ResponseEntity.badRequest()
                .body("Invalid api request"+ex.getMessage());
    }

}
