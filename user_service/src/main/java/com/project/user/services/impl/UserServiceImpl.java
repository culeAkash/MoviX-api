package com.project.user.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.project.user.enums.Active;
import com.project.user.enums.Role;
import com.project.user.payloads.AuthUserDTO;
import com.project.user.payloads.UserDTO;
import com.project.user.requests.RegisterRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.project.user.entities.User;
import com.project.user.exceptions.ResourceNotFoundException;
import com.project.user.payloads.ReviewDto;
import com.project.user.repositories.UserRepository;
import com.project.user.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);
	
	
	//Service method for creating new user
	@Override
	public User createNewUser(RegisterRequest request)  {
		User toSave = User.builder()
				.name(request.getName())
				.password(passwordEncoder.encode(request.getPassword()))
				.email(request.getEmail())
				.about(request.getAbout())
				.role(Role.USER)
				.active(Active.ACTIVE)
				.build();
		return userRepository.save(toSave);
	}

	//Service method fro updating already existing user. It will give exception when user is not present or updating user is not logged in
	@Override
	public UserDTO updateUser(UserDTO userDTO, Long userId){
		User toUpdate = this.userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));

		toUpdate.setName(userDTO.getName());
		toUpdate.setAbout(userDTO.getAbout());

		User updatedUser = this.userRepository.save(toUpdate);

		return mapper.map(updatedUser, UserDTO.class);
	}

	//Service method for deleting user
	//It will give exception when user is not present or updating user is not logged in
	@Override
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));

		userRepository.delete(user);
	}

	//Service method for getting user by id
	@Override
	public UserDTO getUserById(Long userId) {
        User userById = userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		return mapper.map(userById, UserDTO.class);
	}

	//Service method for getting all users
	@Override
	public List<UserDTO> getAllUsers() {
        List<User> allActiveUsers = userRepository.findAllByActive(Active.ACTIVE);
		List<UserDTO> allActiveUsersDTO = new ArrayList<>();
		allActiveUsers.forEach((user)->allActiveUsersDTO.add(mapper.map(user, UserDTO.class)));
		return allActiveUsersDTO;
	}

	@Override
	public AuthUserDTO getUserByEmail(String email) {
		User userByEmail =  userRepository.findByEmail(email).orElse(null);
		return mapper.map(userByEmail, AuthUserDTO.class);
	}



	public ReviewDto getDto(Object forObject) {
//		System.out.println(forObject);
		ReviewDto dto = this.mapper.map(forObject, ReviewDto.class);
		return dto;
	}




}
