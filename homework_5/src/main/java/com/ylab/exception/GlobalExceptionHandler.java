package com.ylab.exception;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Глобальный обработчик ошибок, возникающих в случаях отсутствия данных, добавления уже существующих данных
 * и отрицательных проверок на валидность полей автомобиля
 */
@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    /**
     * Глобальный обработчик ошибок, возникающих при валидации полей
     * @param exception - исключение
     * @return ответ со статусом 400 и отображением ошибок
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Глобальный обработчик ошибок, возникающих при попытке добавления уже существующих значений таблицы
     * @param exception - исключение
     * @return ответ со статусом 409 и отображением ошибки
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleNullValueExceptions(IllegalArgumentException exception) {
        log.log(Level.ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    /**
     * Глобальный обработчик ошибок, возникающих при отсутствии значения
     * @param exception - исключение
     * @return ответ со статусом 404 и отображением ошибки
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleAlreadyExistValueExceptions(NullPointerException exception) {
        log.log(Level.ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    /**
     * Глобальный обработчик ошибок, возникающих при неправильных данных для авторизации
     * @param exception - исключение
     * @return ответ со статусом 409 и отображением ошибки
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> badCredentialExceptions(BadCredentialsException exception) {
        log.log(Level.ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Некорректный данные пользователя!");
    }
}