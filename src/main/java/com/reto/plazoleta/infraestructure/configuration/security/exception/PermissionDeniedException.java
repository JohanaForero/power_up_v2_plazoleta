package com.reto.plazoleta.infraestructure.configuration.security.exception;

public class PermissionDeniedException extends RuntimeException{
    public PermissionDeniedException(String message) {
        super(message);
    }
}
