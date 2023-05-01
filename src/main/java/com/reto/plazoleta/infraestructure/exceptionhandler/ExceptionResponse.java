package com.reto.plazoleta.infraestructure.exceptionhandler;

public enum ExceptionResponse {
    NO_DATA_FOUND("No data found for the requested petition"),
    EMPTY_FIELDS("Fields cannot be empty"),
    INVALID_DATA("Rectify the field format is incorrect"),
    AUTHENTICATION_FAILED("Failed to parse JWT token"),
    PERMISSION_DENIED("Permission denied you are not authorized to use this service"),
    TOKEN_INVALID("Username or role in the token is invalid"),
    USER_DOES_NOT_EXIST("User not found"),
    ROLE_UNAUTHORIZED("The user id does not have the required role to use this action");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}