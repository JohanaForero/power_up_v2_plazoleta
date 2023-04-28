package com.reto.plazoleta.domain.exception;

public class EmptyFieldsException extends RuntimeException {
    public EmptyFieldsException(String message) {
        super(message);
    }
}
