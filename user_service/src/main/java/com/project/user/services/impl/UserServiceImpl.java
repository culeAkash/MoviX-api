package com.project.user.services.impl;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.user.auth.AuthStorage;
import com.project.user.entities.User;
import com.project.user.exceptions.ForbiddenRequestException;
import com.project.user.exceptions.InvalidCredentialsException;
import com.project.user.exceptions.ResourceNotFoundException;
import com.project.user.exceptions.UserNotLoggedInException;
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
		
		if(findUserByEmail!=null)
			throw new InvalidCredentialsException("Email is already in use...Please try some other email");
		
		user.setRole(0);
		User newUser = userRepository.save(user);
		return newUser;
	}

	//Service method fro updating already existing user. It will give exception when user is not present or updating user is not logged in
	@Override
	public User updateUser(User user, Long userId) throws ForbiddenRequestException {
		if(!AuthStorage.isUserLoggedIn(userId)) {
//			System.out.println(AuthStorage.LOGGED_IN_USER_DATA.get(AuthStorage.USER).getUserId());
			throw new ForbiddenRequestException();
		}
		
		
		User reqUser = userRepository.findById(userId)
		.orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		
		

		user.setUserId(userId);
		user.setRole(reqUser.getRole());
		User updatedUser = userRepository.save(user);
		return updatedUser;
	}

	//Service method for deleting user
	//It will give exception when user is not present or updating user is not logged in
	@Override
	public User deleteUser(Long userId) throws ForbiddenRequestException {
		if(!AuthStorage.isUserLoggedIn(userId)) {
//			System.out.println(AuthStorage.LOGGED_IN_USER_DATA.get(AuthStorage.USER).getUserId());
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

		//Get user with user id from user database
		User user = userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		
				
		List<ReviewDto> reviews = this.getReviewByUserId(userId);
		logger.info("{}",reviews);
		user.setReviews(reviews);
		return user; 
	}

	//Service method for getting all users
	@Override
	public List<User> getAllUsers() {
//		if(AuthStorage.isUserLoggedIn().equals("noauth")) {
//			throw new UserNotLoggedInException();
//		}

		List<User> allUsers = userRepository.findAll();
		allUsers.forEach(user->{
			Long userId = user.getUserId();
			List<ReviewDto> reviewsArr = this.getReviewByUserId(userId);
			user.setReviews(reviewsArr);
		});
		return allUsers;
	}
	
	//service mthod to pull all reviews of a user from movie microservice
	private List<ReviewDto> getReviewByUserId(Long userId) {
	
		//Get the reviews from Movie Microservice
				 List<Object> forObject = restTemplate.getForObject("http://movie-service/movies/reviews/user/"+userId, List.class);
				 List<ReviewDto> reviews = new ArrayList<>();
				forObject.forEach((obj)->{
					System.out.println(obj);
					ReviewDto dto = getDto(obj);
					reviews.add(dto);
				});
				return reviews;
	}

	//Service method for getting users by username
	@Override
	public List<User> getUsersByUserName(String userName) {
		
		List<User> findUserByUserName = userRepository.findUserByUserName(userName);
		
		
		if(findUserByUserName.isEmpty()) {
			throw new ResourceNotFoundException("Users", "userName", userName);
		}

		findUserByUserName.forEach(user->{
			Long userId = user.getUserId();
			List<ReviewDto> reviews = this.getReviewByUserId(userId);
			user.setReviews(reviews);
		});
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
		return findById;
	}
	
}
