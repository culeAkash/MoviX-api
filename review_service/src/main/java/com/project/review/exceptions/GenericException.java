package com.project.review.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class GenericException extends RuntimeException{
    private final String message;
    private final HttpStatus httpStatus;

    public GenericException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
