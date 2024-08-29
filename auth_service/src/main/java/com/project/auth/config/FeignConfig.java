package com.project.auth.config;


import com.project.auth.client.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder getErrorDecoder(){
        return new CustomErrorDecoder();
    }
}
