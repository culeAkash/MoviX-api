package com.project.user.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.project.user.enums.Active;
import com.project.user.enums.Role;
import com.project.user.exceptions.DuplicateColumnException;
import com.project.user.exceptions.GenericErrorResponse;
import com.project.user.payloads.AuthUserDTO;
import com.project.user.payloads.UserDTO;
import com.project.user.requests.RegisterRequest;
import com.project.user.services.BloomFilterService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


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


	@Autowired
	private BloomFilterService bloomFilterService;
	
	
	//Service method for creating new user
	@Override
	public User createNewUser(RegisterRequest request) throws DuplicateColumnException {
//		Optional<User> userByEmail = this.userRepository.findByEmail(request.getEmail());

		if(bloomFilterService.getEmailBloomFilter().contains(request.getEmail())){
			throw new DuplicateColumnException("Email is already taken, try some other email");
		}

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

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Object principal = authentication.getPrincipal();

		logger.debug("Pricipal: " + principal);

		User userByPrincipal = this.userRepository.findByEmail(String.valueOf(principal)).get();

		if(!Objects.equals(userByPrincipal.getUserId(), userId)){
			throw new GenericErrorResponse("Not allowed to perform this action", HttpStatus.FORBIDDEN);
		}

		User toUpdate = this.userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));

		System.out.println(userDTO);

		toUpdate.setName(userDTO.getName());

		toUpdate.setAbout(userDTO.getAbout());

		toUpdate.setEmail(userDTO.getEmail());

		User updatedUser = this.userRepository.save(toUpdate);

		return mapper.map(updatedUser, UserDTO.class);
	}

	//Service method for deleting user
	//It will give exception when user is not present or updating user is not logged in
	@Override
	public void deleteUser(Long userId) {

		User user = userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String principal = authentication.getPrincipal().toString();

		User userByPrincipal = this.userRepository.findByEmail(principal).get();

		if(!Objects.equals(userByPrincipal.getUserId(), userId))
			throw new GenericErrorResponse("Not allowed to perform this action", HttpStatus.FORBIDDEN);

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
		User userByEmail =  userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User","email",email));
		return mapper.map(userByEmail, AuthUserDTO.class);
	}



	public ReviewDto getDto(Object forObject) {
//		System.out.println(forObject);
		ReviewDto dto = this.mapper.map(forObject, ReviewDto.class);
		return dto;
	}




}
