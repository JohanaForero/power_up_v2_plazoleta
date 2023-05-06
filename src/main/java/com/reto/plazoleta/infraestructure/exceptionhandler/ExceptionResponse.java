package com.reto.plazoleta.infraestructure.exceptionhandler;

public enum ExceptionResponse {
    NO_DATA_FOUND("No data found for the requested petition"),
    EMPTY_FIELDS("Fields cannot be empty"),
    INVALID_DATA("Rectify the field format is incorrect"),
    AUTHENTICATION_FAILED("Failed to parse JWT token"),
    TOKEN_INVALID("The token is invalid"),
    USER_DOES_NOT_EXIST("User id not found"),
    ACCESS_DENIED("Prohibited you do not have the necessary role for authorization");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}