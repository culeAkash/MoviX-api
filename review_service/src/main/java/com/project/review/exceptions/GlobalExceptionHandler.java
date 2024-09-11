package com.project.review.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ApiErrorResponse> genericErrorResponseHandler(GenericException genericErrorResponse){
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .message(genericErrorResponse.getMessage())
                .isSuccessful(false)
                .build();
        return new ResponseEntity<>(apiErrorResponse, genericErrorResponse.getHttpStatus());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> genericExceptionHandler(Exception exception){
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .message(exception.getMessage())
                .isSuccessful(false)
                .build();

        return new ResponseEntity<>(apiErrorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
