package com.project.user.services;

import java.util.List;

import com.project.user.entities.User;

public interface UserService {

	
	
public User createNewUser(User user);
	
	public User updateUser(User user,Long userId);
	
	public User deleteUser(Long userId);
	
	public User getUserById(Long userId);
	
	public List<User> getAllUsers();
	
	public List<User> getUsersByUserName(String userName);

	Boolean confirmUserService(Long userId);

	User getUserService(Long userId);
	
}
