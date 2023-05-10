package com.reto.plazoleta.domain.exception;

public class RestrictedAccessException extends RuntimeException {

    public RestrictedAccessException(String message) {
        super(message);
    }
}
