package com.project.movie.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.movie.entities.Review;
import com.project.movie.exceptions.ForbiddenRequestException;
import com.project.movie.services.ReviewService;

import jakarta.validation.Valid;

@RestController
public class ReviewController {
	
	Logger logger = LoggerFactory.getLogger(ReviewController.class);

	@Autowired
	private ReviewService reviewService;

	@PostMapping("/movies/{movieId}/user/{userId}/reviews")
	public ResponseEntity<Review> createReview(@Valid @RequestBody Review review,@PathVariable("movieId") Long movieId,@PathVariable("userId") Long userId) throws ForbiddenRequestException {
		Review res = reviewService.createReview(review, movieId,userId);
		return new ResponseEntity<Review>(res,HttpStatus.CREATED);

	}

	@GetMapping("/movies/{movieId}/reviews")
	public ResponseEntity<List<Review>> getAllReviewsOfMovie(@PathVariable Long movieId){
		logger.info("The client demanded all reviews of movie with movieId {}",movieId);
		List<Review> reviews = reviewService.getAllReviewsOfMovie(movieId);
		return new ResponseEntity<List<Review>>(reviews,HttpStatus.OK);

	}

	@PutMapping("/movies/reviews/{reviewId}")
	public ResponseEntity<Review> updateReview(@PathVariable Long reviewId,@Valid @RequestBody Review review) throws ForbiddenRequestException{
		Review res =  reviewService.updateReview(reviewId, review);
		return new ResponseEntity<Review>(res,HttpStatus.OK);
	}


	@DeleteMapping("/movies/reviews/{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) throws ForbiddenRequestException{
		reviewService.deleteReview(reviewId);
		return new ResponseEntity<String>("Deleted Successfully",HttpStatus.ACCEPTED);

	}
	
	
	@GetMapping("/movies/reviews/user/{userId}")
	public ResponseEntity<List<Review>> getReviewsByUserId(@PathVariable Long userId){
		List<Review> allReviewsByUserId = this.reviewService.getAllReviewsByUserId(userId);
		return new ResponseEntity<>(allReviewsByUserId,HttpStatus.ACCEPTED);
	}





}
