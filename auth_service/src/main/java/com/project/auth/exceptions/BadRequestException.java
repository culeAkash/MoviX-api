package com.project.auth.exceptions;


import java.util.Map;

public class BadRequestException extends RuntimeException {
    Map<String,String> errors;
    public BadRequestException(Map<String,String> errors){
        this.errors = errors;
    }
}
