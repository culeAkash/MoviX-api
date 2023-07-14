package com.project.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.user.auth.AuthStorage;
import com.project.user.auth.LoginData;
import com.project.user.entities.User;
import com.project.user.exceptions.InvalidCredentialsException;
import com.project.user.payloads.ApiResponse;
import com.project.user.services.AuthService;


@RestController
public class AuthController {

	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/users/auth/v1/login")
	public ResponseEntity<User> loginUser(@RequestBody LoginData loginData)throws InvalidCredentialsException{
		this.authService.doLogin(loginData);
		return new ResponseEntity<User>(AuthStorage.LOGGED_IN_USER_DATA.get(AuthStorage.USER),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/users/logout")
	public ResponseEntity<ApiResponse> logout(){
		this.authService.doLogOut();
		return new ResponseEntity<ApiResponse>(new ApiResponse("Logout successful",true),HttpStatus.ACCEPTED);
	}
}
