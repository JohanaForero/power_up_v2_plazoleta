package com.reto.plazoleta.infraestructure.configuration.security.exception;

public class AuthenticationFailedException extends RuntimeException{

    public AuthenticationFailedException() {
        super("Failed to parse JWT token");
    }
}
