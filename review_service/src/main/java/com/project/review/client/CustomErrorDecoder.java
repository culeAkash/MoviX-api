package com.project.review.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.review.exceptions.GenericException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Exception decode(String s, Response response) {
        Map<String,Object> errorMap = null;
        try(InputStream bodyIs = response.body().asInputStream()){
            System.out.println(bodyIs);
            errorMap = objectMapper.readValue(bodyIs,Map.class);
            System.out.println(errorMap.entrySet().toString());
        }
        catch (IOException ex){
            return new GenericException(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new GenericException((String) errorMap.getOrDefault("message","Something unexpected happened"),HttpStatus.valueOf(response.status()));
    }
}
