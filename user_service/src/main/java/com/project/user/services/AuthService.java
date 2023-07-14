package com.project.user.services;

import com.project.user.auth.LoginData;
import com.project.user.entities.User;
import com.project.user.exceptions.InvalidCredentialsException;

public interface AuthService {

	public User doLogin(LoginData data) throws InvalidCredentialsException;


	public void doLogOut();
}
