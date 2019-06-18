package cl.queltehues.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthorizationServiceException.class)
    public ResponseEntity<String> handleAuthorizationException(AuthorizationServiceException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
