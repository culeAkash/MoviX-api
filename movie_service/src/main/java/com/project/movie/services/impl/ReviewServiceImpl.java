package com.project.movie.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.movie.entities.Movie;
import com.project.movie.entities.Review;
import com.project.movie.exceptions.ForbiddenRequestException;
import com.project.movie.exceptions.ResourceNotFoundException;
import com.project.movie.exceptions.UserNotLoggedInException;
import com.project.movie.payloads.UserDto;
import com.project.movie.repositories.MovieRepository;
import com.project.movie.repositories.ReviewRepository;
import com.project.movie.services.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
	

	//	private static int count = 20;
	@Override
	public List<Review> getAllReviewsOfMovie(Long movieId) {
		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(()->new ResourceNotFoundException("Movie", "movieId", movieId));
		List<Review> reviews = movie.getReviews();
		
//		for(Review review : reviews) {
//			System.out.println(review.getUserId());
//		}
		
		
		//set userdto for all reviews now
				reviews.forEach((review)->{
					Long id = review.getUserId();
					Object userTemp = this.restTemplate.getForObject("http://user-service/services/users/getUser/"+id, Object.class);
//					System.out.println(userTemp);
					if(userTemp==null) {
						review.setUser(null);
						review.setUserId(null);
					}else {
					//convert to user dto
					UserDto userDto = this.modelMapper.map(userTemp, UserDto.class);
					review.setUser(userDto);}
				});
		return reviews;
	}

	@Override
	public Review updateReview(Long reviewId,Review review) throws ForbiddenRequestException {
		Review req = reviewRepository.findById(reviewId)
				.orElseThrow(()->new ResourceNotFoundException("Review", "reviewId", reviewId));
		Long userId = req.getUserId();
		
		
		//check if loggedIn user can do this operation
		if(!checkIfLoggedInUserAllowed(userId)) {
			logger.warn("User with userId {} has to be logged in to perform this action",userId);
			throw new ForbiddenRequestException();
		}		
		
		req.setReviewContent(review.getReviewContent());
		reviewRepository.save(req);
		return req;
	}

	@Override
	public void deleteReview(Long reviewId) throws ForbiddenRequestException {
		
		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(()->new ResourceNotFoundException("Review", "reviewId", reviewId));
		
Long userId = review.getUserId();
		
		reviewRepository.delete(review);
	}

	@Override
	public Review createReview(Review review,Long movieId,Long userId) throws ForbiddenRequestException {
		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(()->new ResourceNotFoundException("Movie", "movieId", movieId));

		review.setMovie(movie);
		
		Boolean isUserPresent = this.restTemplate.getForObject("http://user-service/services/users/confirmUserPresence/"+userId, Boolean.class);
		
		if(!isUserPresent) {
			throw new ResourceNotFoundException("User", "userId", userId);
		}
		//		review.setReviewId(count++);
		//		movie.getReviews().add(review);
		//		user.getReviewsAdded().add(review);,
		
		
		//check if loggedIn user can do this operation
		if(!checkIfLoggedInUserAllowed(userId))
			throw new ForbiddenRequestException();
		
		review.setUserId(userId);

		reviewRepository.save(review);

		//		this.movieRepository.save(movie);
		//		this.userRepository.save(user);

		return review;
	}

	@Override
	public List<Review> getAllReviewsByUserId(Long userId) throws ResourceNotFoundException {
		List<Review> findReviewByUserId = this.reviewRepository.findReviewByUserId(userId);
		if(findReviewByUserId.size()==0)
			throw new ResourceNotFoundException("Reviews", "userId", userId);
		
		//set userdto for all reviews now
		findReviewByUserId.forEach((review)->{
			Long id = review.getUserId();
			Object userTemp = this.restTemplate.getForObject("http://user-service/services/users/getUser/"+id, Object.class);
			if(userTemp==null)
				throw new ResourceNotFoundException("User", "userId", userId);
			//convert to user dto
			UserDto userDto = this.modelMapper.map(userTemp, UserDto.class);
			review.setUser(userDto);
		});
		
		return findReviewByUserId;
	}
	
	public Boolean checkIfLoggedInUserAllowed(Long userId) {
		Boolean role = this.restTemplate.getForObject("http://user-service/services/users/isLoggedIn/"+userId, Boolean.class);
		return role;
	}
		

}
