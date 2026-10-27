package com.shopmaster.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Convierte errores de validación en una respuesta JSON fácil de entender. */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AuthResponse> manejarDatosInvalidos(MethodArgumentNotValidException excepcion) {
        String mensaje = excepcion.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Los datos enviados no son válidos.");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new AuthResponse(false, mensaje));
    }
}