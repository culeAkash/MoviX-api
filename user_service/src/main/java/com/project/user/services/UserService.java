package com.project.user.services;

import java.util.List;

import com.project.user.entities.User;
import com.project.user.payloads.UserDTO;
import com.project.user.requests.RegisterRequest;

public interface UserService {

	
	
public User createNewUser(RegisterRequest request);
	
	public UserDTO updateUser(UserDTO userDTO, Long userId);
	
	public void deleteUser(Long userId);
	
	public User getUserById(Long userId);
	
	public List<User> getAllUsers();

	User getUserService(Long userId);

	User getUserByEmail(String email);
	
}
