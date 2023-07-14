package com.project.user.services;

import java.util.List;

import com.project.user.entities.User;
import com.project.user.exceptions.ForbiddenRequestException;
import com.project.user.exceptions.InvalidCredentialsException;
import com.project.user.exceptions.UserNotLoggedInException;
import com.project.user.payloads.PasswordDTO;

public interface UserService {

	
	
public User createNewUser(User user) throws InvalidCredentialsException;
	
	public User updateUser(User user,Long userId) throws ForbiddenRequestException;
	
	public User deleteUser(Long userId) throws ForbiddenRequestException;
	
	public User getUserById(Long userId) throws ForbiddenRequestException;
	
	public List<User> getAllUsers();
	
	public List<User> getUsersByUserName(String userName) throws UserNotLoggedInException;

	Boolean confirmUserService(Long userId);

	User getUserService(Long userId);

	public User changePassword(Long userId, PasswordDTO passwordObject) throws ForbiddenRequestException, InvalidCredentialsException;
	
}
