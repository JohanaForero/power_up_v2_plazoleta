package com.reto.plazoleta.infraestructure.exceptionhandler;

import com.reto.plazoleta.domain.exception.EmptyFieldsException;
import com.reto.plazoleta.domain.exception.InvalidDataException;
import com.reto.plazoleta.infraestructure.configuration.security.exception.AuthenticationFailedException;
import com.reto.plazoleta.infraestructure.configuration.security.exception.PermissionDeniedException;
import com.reto.plazoleta.infraestructure.configuration.security.exception.TokenInvalidException;
import com.reto.plazoleta.infraestructure.configuration.security.exception.UserDoesNotExistException;
import com.reto.plazoleta.infraestructure.exception.NoDataFoundException;
import com.reto.plazoleta.infraestructure.exception.RoleUnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(
            NoDataFoundException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NO_DATA_FOUND.getMessage()));
    }

    @ExceptionHandler(EmptyFieldsException.class)
    public ResponseEntity<Map<String, String>> handleEmptyFieldsException(
            EmptyFieldsException ignoredEmptyFieldsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.EMPTY_FIELDS.getMessage()));
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Map<String, String>> handleInvalidDataException(
            InvalidDataException ignoredInvalidDataException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.INVALID_DATA.getMessage()));
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationFailedException(
            AuthenticationFailedException ignoredAuthenticationFailedException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.AUTHENTICATION_FAILED.getMessage()));
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<Map<String, String>> handlePermissionDeniedException(
            PermissionDeniedException ignoredPermissionDeniedException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.PERMISSION_DENIED.getMessage()));
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<Map<String, String>> handleTokenInvalidException(
            TokenInvalidException ignoredTokenInvalidException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.TOKEN_INVALID.getMessage()));
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<Map<String, String>> handleUserDoesNotExistException(
            UserDoesNotExistException ignoredUserDoesNotExistException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.USER_DOES_NOT_EXIST.getMessage()));
    }

    @ExceptionHandler(RoleUnauthorizedException.class)
    public ResponseEntity<Map<String, String>> handleRoleUnauthorizedException(
            RoleUnauthorizedException ignoredRoleUnauthorizedException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.ROLE_UNAUTHORIZED.getMessage()));
    }

}
