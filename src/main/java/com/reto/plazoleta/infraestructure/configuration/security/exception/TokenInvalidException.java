package com.reto.plazoleta.infraestructure.configuration.security.exception;

public class TokenInvalidException extends RuntimeException{
    public TokenInvalidException(String message) {
        super(message);
    }
}
