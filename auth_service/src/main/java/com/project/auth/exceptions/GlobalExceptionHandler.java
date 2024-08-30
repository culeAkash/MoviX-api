package com.project.auth.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<ApiResponse> usernameOrPasswordInvalidException(WrongCredentialsException exception) {
        return new ResponseEntity<>(new ApiResponse(exception.getMessage(),false), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(GenericErrorResponse.class)
    public ResponseEntity<ApiResponse> genericErrorResponse(GenericErrorResponse ex){
        return new ResponseEntity<>(new ApiResponse(ex.getMessage(),false),ex.getHttpStatus());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<ApiResponse> usernameNotFoundExceptionHandler(UsernameNotFoundException exception){
        return new ResponseEntity<>(new ApiResponse(exception.getMessage(),false),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Map<String,String>> handleBadRequestException(BadRequestException e){
        return ResponseEntity.badRequest().body(e.errors);
    }



    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllException(Exception ex) {
        System.out.println(ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }



}
