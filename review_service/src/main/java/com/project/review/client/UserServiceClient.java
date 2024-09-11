package com.project.review.client;


import com.project.review.payloads.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service",path = "/api/v1/users/public")
public interface UserServiceClient {

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserDTO> getUserByUserEmail(@PathVariable("email") String email);
}
