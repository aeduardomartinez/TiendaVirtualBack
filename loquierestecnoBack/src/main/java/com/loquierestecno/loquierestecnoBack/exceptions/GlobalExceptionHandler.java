package com.loquierestecno.loquierestecnoBack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> manejarEmailExistente(EmailAlreadyExistsException ex) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
        respuesta.put("error", "Bad Request");
        respuesta.put("message", ex.getMessage());

        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }
}
