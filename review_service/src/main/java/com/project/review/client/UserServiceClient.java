package com.project.review.client;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service",path = "/api/v1/users")
public interface UserServiceClient {
}
