package com.project.user.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.user.exceptions.GenericErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        try(InputStream body = response.body().asInputStream()){
            Map<String,String> errors =
                    mapper.readValue(IOUtils.toString(body, StandardCharsets.UTF_8),Map.class);
            return GenericErrorResponse.builder()
                    .message(errors.get("error"))
                    .httpStatus(HttpStatus.valueOf(response.status()))
                    .build();
        } catch (IOException e) {
            throw GenericErrorResponse.builder()
                    .message(e.getMessage())
                    .httpStatus(HttpStatus.valueOf(response.status()))
                    .build();
        }
    }
}
