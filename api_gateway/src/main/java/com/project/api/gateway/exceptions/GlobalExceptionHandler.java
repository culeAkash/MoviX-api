package com.project.api.gateway.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }


    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleJwtException(JwtException ex) {
        String message = ex.getMessage();
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        // Customize response for expired tokens
        if (ex instanceof ExpiredJwtException) {
            message = "Token has expired. Please login again.";
        }
        else if(ex instanceof MalformedJwtException){
            message = "Token is invalid, Please login again";
        }


        return ResponseEntity.status(status).body(message);
    }
}
