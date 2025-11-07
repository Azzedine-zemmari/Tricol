package com.tricol.Tricol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ProduitNotFound.class)
    public ResponseEntity<ApiErreur> produitNotFoundHandler(ProduitNotFound e){
        ApiErreur apiErreur = new ApiErreur();
        apiErreur.setMessage(e.getMessage());
        apiErreur.setCode(HttpStatus.NOT_FOUND.value());
        apiErreur.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(apiErreur, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(FournisseurNotFound.class)
    public ResponseEntity<ApiErreur> fournisseurNotFoundHandler(FournisseurNotFound e){
        ApiErreur apiErreur = new ApiErreur();
        apiErreur.setMessage(e.getMessage());
        apiErreur.setCode(HttpStatus.NOT_FOUND.value());
        apiErreur.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(apiErreur,HttpStatus.NOT_FOUND);
    }
}
