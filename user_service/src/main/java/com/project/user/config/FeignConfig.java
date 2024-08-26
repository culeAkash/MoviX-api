package com.project.user.config;

import com.project.user.client.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    public ErrorDecoder errorDecoder(){
        return new CustomErrorDecoder();
    }
}
