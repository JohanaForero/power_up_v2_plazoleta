package com.reto.plazoleta.domain.exception;

public class RestaurantEmployeeNotExistException extends RuntimeException {
    public RestaurantEmployeeNotExistException(String message) {
        super(message);
    }
}

