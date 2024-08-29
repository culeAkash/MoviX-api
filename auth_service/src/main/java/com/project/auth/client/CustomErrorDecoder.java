package com.project.auth.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.auth.exceptions.BadRequestException;
import com.project.auth.exceptions.GenericErrorResponse;
import com.project.auth.exceptions.InternalServerErrorException;
import com.project.auth.exceptions.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ObjectMapper mapper = new ObjectMapper();


    Logger logger = LoggerFactory.getLogger(CustomErrorDecoder.class);

    @SneakyThrows
    @Override
    public Exception decode(String methodKey, Response response) {
        try(InputStream body = response.body().asInputStream()){
            Map<String,String> errors =
                    mapper.readValue(IOUtils.toString(body, StandardCharsets.UTF_8),Map.class);
            throw new BadRequestException(errors);
        } catch (IOException e) {
            throw GenericErrorResponse.builder()
                    .message(e.getMessage())
                    .httpStatus(HttpStatus.valueOf(response.status()))
                    .build();
        }
    }
}
