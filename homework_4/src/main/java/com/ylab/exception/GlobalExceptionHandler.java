package com.ylab.exception;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Глобальный обработчик ошибок, возникающих в случаях отсутствия данных, добавления уже существующих данных
 * и отрицательных проверок на валидность полей автомобиля
 */
@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationCarDataException.class)
    public ResponseEntity<String> handleValidationExceptions(ValidationCarDataException ex) {
        log.log(Level.ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleNullValueExceptions(IllegalArgumentException ex) {
        log.log(Level.ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleAlreadyExistValueExceptions(NullPointerException ex) {
        log.log(Level.ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}