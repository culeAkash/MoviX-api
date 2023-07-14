package com.project.user.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.user.auth.AuthStorage;
import com.project.user.auth.LoginData;
import com.project.user.entities.User;
import com.project.user.exceptions.InvalidCredentialsException;
import com.project.user.repositories.UserRepository;
import com.project.user.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User doLogin(LoginData data) throws InvalidCredentialsException {
		System.out.println(data.getEmail());
		final User userByEmail = userRepository.findUserByEmail(data.getEmail());
		if(userByEmail==null) {
			throw new InvalidCredentialsException("Email is not present or wrong!");
		}
		if(!userByEmail.getPassword().equals(data.getPassword())) {
			throw new InvalidCredentialsException("Password is incorrect");
		}
		AuthStorage.LOGGED_IN_USER_DATA.put(AuthStorage.USER, userByEmail);
		return userByEmail;



	}

	@Override
	public void doLogOut() {
		AuthStorage.LOGGED_IN_USER_DATA.remove(AuthStorage.USER);
	}



	
}
