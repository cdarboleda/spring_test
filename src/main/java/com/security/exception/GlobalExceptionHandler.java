package com.security.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.UnexpectedTypeException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de errores específicos de no encontrar entidad
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> customException(CustomException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<String> handleValidationExceptions(UnexpectedTypeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // // Manejo genérico para RuntimeException (que ahora solo captura excepciones
    // generales)
    // @ExceptionHandler(RuntimeException.class)
    // public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body("Error inesperado: " + ex.getMessage());
    // }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

        @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonParseError(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    //Esta otra forma envia el error con atributos clave/valor, ta chevere pero no vamos a usarle
    //     @ExceptionHandler(HttpMessageNotReadableException.class)
    // public ResponseEntity<Map<String, Object>> handleJsonParseError(HttpMessageNotReadableException ex) {
    //     Map<String, Object> response = new HashMap<>();
    //     response.put("error", "Solicitud JSON inválida");
    //     response.put("detalle", ex.getMessage());
    //     response.put("status", HttpStatus.BAD_REQUEST.value());
    //     return ResponseEntity.badRequest().body(response);
    // }

}
