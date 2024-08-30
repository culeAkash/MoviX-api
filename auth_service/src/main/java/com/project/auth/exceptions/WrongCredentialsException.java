package com.project.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class WrongCredentialsException extends Exception {

    public WrongCredentialsException(String message){
        super(message);
    }
}
