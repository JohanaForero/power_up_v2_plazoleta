package com.reto.plazoleta.infraestructure.configuration.security.exception;

public class UserDoesNotExistException extends RuntimeException{

    public UserDoesNotExistException(String message) {
        super(message);
    }
}
