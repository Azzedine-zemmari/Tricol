package com.tricol.Tricol.exception;

public class ProduitNotFound extends RuntimeException {
    public ProduitNotFound(String message) {
        super(message);
    }
}
