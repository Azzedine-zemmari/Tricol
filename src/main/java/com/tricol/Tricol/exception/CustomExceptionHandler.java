package com.tricol.Tricol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

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
}
