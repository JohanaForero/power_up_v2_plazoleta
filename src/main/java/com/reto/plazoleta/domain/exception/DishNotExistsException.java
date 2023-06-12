package com.reto.plazoleta.domain.exception;

public class DishNotExistsException extends RuntimeException{
    
    public DishNotExistsException(String message) {
        super(message);
    }
}
