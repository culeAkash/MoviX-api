package com.project.auth.controllers;

import com.project.auth.exceptions.WrongCredentialsException;
import com.project.auth.payloads.RegisterDTO;
import com.project.auth.payloads.TokenDTO;
import com.project.auth.requests.LoginRequest;
import com.project.auth.requests.RegisterRequest;
import com.project.auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginRequest request) throws WrongCredentialsException {
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterDTO> register(@RequestBody RegisterRequest request){
        return new ResponseEntity<>(authService.register(request),HttpStatus.CREATED);
    }

}
