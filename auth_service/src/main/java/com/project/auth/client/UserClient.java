package com.project.auth.client;


import com.project.auth.payloads.RegisterDTO;
import com.project.auth.payloads.UserDTO;
import com.project.auth.requests.RegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.CacheRequest;

@FeignClient(name = "user-service",path = "/api/v1/users/public")
public interface UserClient {
    @PostMapping("/save")
    ResponseEntity<RegisterDTO> saveUser(@RequestBody RegisterRequest request);

    @GetMapping("/getUserByEmail/{email}")
    ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email);
}
