package com.reto.plazoleta.infraestructure.exception;

public class RoleUnauthorizedException extends RuntimeException{
    public RoleUnauthorizedException(String message) {
        super(message);
    }
}
