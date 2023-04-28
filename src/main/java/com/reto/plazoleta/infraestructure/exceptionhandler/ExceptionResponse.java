package com.reto.plazoleta.infraestructure.exceptionhandler;

public enum ExceptionResponse {
    NO_DATA_FOUND("No data found for the requested petition"),
    EMPTY_FIELDS("Fields cannot be empty"),
    INVALID_DATA("Rectify the field format is incorrect");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}