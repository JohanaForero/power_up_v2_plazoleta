package com.reto.plazoleta.infraestructure.exception;

public class UserInTokenIsInvalidException extends RuntimeException{
    public UserInTokenIsInvalidException(String message) {
        super(message);
    }
}
