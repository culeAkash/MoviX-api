package com.project.user.services;

import java.util.List;

import com.project.user.entities.User;
import com.project.user.payloads.AuthUserDTO;
import com.project.user.payloads.UserDTO;
import com.project.user.requests.RegisterRequest;

public interface UserService {

	
	
public User createNewUser(RegisterRequest request);
	
	public UserDTO updateUser(UserDTO userDTO, Long userId);
	
	public void deleteUser(Long userId);
	
	public UserDTO getUserById(Long userId);
	
	public List<UserDTO> getAllUsers();

	public AuthUserDTO getUserByEmail(String email);
	
}
