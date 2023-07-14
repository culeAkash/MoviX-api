package com.project.user.services.impl;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.user.auth.AuthStorage;
import com.project.user.entities.User;
import com.project.user.exceptions.ForbiddenRequestException;
import com.project.user.exceptions.InvalidCredentialsException;
import com.project.user.exceptions.ResourceNotFoundException;
import com.project.user.exceptions.UserNotLoggedInException;
import com.project.user.payloads.PasswordDTO;
import com.project.user.payloads.ReviewDto;
import com.project.user.repositories.UserRepository;
import com.project.user.services.AuthService;
import com.project.user.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AuthService authService;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);
	
	
	//Service method for creating new user
	@Override
	public User createNewUser(User user) throws InvalidCredentialsException {
		User findUserByEmail = this.userRepository.findUserByEmail(user.getEmail());
		
		//logging user coming from client
		logger.info("User sent the data: {}",findUserByEmail);
		
		if(findUserByEmail!=null)
			throw new InvalidCredentialsException("Email is already in use...Please try some other email");
		
		user.setRole(0);
//		user.setProfileImage("user.png");
		logger.debug("Role is set successfully");
		User newUser = userRepository.save(user);
		logger.debug("The created user is {}",newUser);
		return newUser;
	}

	//Service method fro updating already existing user. It will give exception when user is not present or updating user is not logged in
	@Override
	public User updateUser(User user, Long userId) throws ForbiddenRequestException {
		if(!AuthStorage.isUserLoggedIn(userId)) {
//			System.out.println(AuthStorage.LOGGED_IN_USER_DATA.get(AuthStorage.USER).getUserId());
			logger.warn("User is not allowed to access this feature with userId {}",userId);
			throw new ForbiddenRequestException();
		}
		
		
		User reqUser = userRepository.findById(userId)
		.orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		
		

		user.setUserId(userId);
		user.setRole(reqUser.getRole());
		User updatedUser = userRepository.save(user);
		logger.debug("The updated user datails is: {}",updatedUser);
		return updatedUser;
	}

	//Service method for deleting user
	//It will give exception when user is not present or updating user is not logged in
	@Override
	public User deleteUser(Long userId) throws ForbiddenRequestException {
		if(!AuthStorage.isUserLoggedIn(userId)) {
//			System.out.println(AuthStorage.LOGGED_IN_USER_DATA.get(AuthStorage.USER).getUserId());
			logger.warn("User is not allowed to access this feature with userId {}",userId);
			throw new ForbiddenRequestException();
		}
		
		
		User user = userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		
		this.authService.doLogOut();

		userRepository.delete(user);

		return user;
	}

	//Service method for getting user by id
	@Override
	public User getUserById(Long userId) {

		logger.info("The client requires user with id: {}",userId);
		//Get user with user id from user database
		User user = userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		
		logger.debug("User data requested: {}",user);
		return user; 
	}

	//Service method for getting all users
	@Override
	public List<User> getAllUsers() {
//		if(AuthStorage.isUserLoggedIn().equals("noauth")) {
//			throw new UserNotLoggedInException();
//		}

		List<User> allUsers = userRepository.findAll();
		return allUsers;
	}

	//Service method for getting users by username
	@Override
	public List<User> getUsersByUserName(String userName) {
		
		List<User> findUserByUserName = userRepository.findUserByUserName(userName);
		
		
		if(findUserByUserName.isEmpty()) {
			throw new ResourceNotFoundException("Users", "userName", userName);
		}
		return findUserByUserName;
		

	}
	
	
	
	public ReviewDto getDto(Object forObject) {
//		System.out.println(forObject);
		ReviewDto dto = this.mapper.map(forObject, ReviewDto.class);
		return dto;
	}
	
	
	
//Service methods for micro-service cross communication
	@Override
	public Boolean confirmUserService(Long userId) {
		User findById = this.userRepository.findById(userId).orElse(null);
		return findById!=null;
	}

	@Override
	public User getUserService(Long userId) {
		User findById = this.userRepository.findById(userId).orElse(null);
		System.out.println(findById);
		return findById;
	}

	@Override
	public User changePassword(Long userId, PasswordDTO passwordObject) throws ForbiddenRequestException, InvalidCredentialsException {
		if(!AuthStorage.isUserLoggedIn(userId)) {
//			System.out.println(AuthStorage.LOGGED_IN_USER_DATA.get(AuthStorage.USER).getUserId());
			logger.warn("User is not allowed to access this feature with userId {}",userId);
			throw new ForbiddenRequestException();
		}
		
		User userByEmail = this.userRepository.findUserByEmail(passwordObject.getEmail());
		
		if(userByEmail==null) {
			logger.warn("User is not present in database with email {}",passwordObject.getEmail());
			throw new InvalidCredentialsException("Email is not present or wrong!");
		}
		
		if(!userByEmail.getPassword().equals(passwordObject.getOldPassword())) {
			logger.warn("User password doesn't match with old password for userId {}",userId);
			throw new InvalidCredentialsException("Given password is wrong, can't change to new password");
		}
		
		logger.debug("The user whose password to be changed is {}",userByEmail);
		userByEmail.setPassword(passwordObject.getNewPassword());
		
		User save = this.userRepository.save(userByEmail);
		logger.debug("The user with changed password is {}",save);
		return save;
	}
	
}
