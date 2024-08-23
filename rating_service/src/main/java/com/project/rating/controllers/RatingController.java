package com.project.rating.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.rating.entities.Rating;
import com.project.rating.payloads.ApiResponse;
import com.project.rating.payloads.MovieRatingResponse;
import com.project.rating.services.RatingService;

import jakarta.validation.Valid;

@RestController
public class RatingController {

	@Autowired
	private RatingService ratingService;

	Logger logger = LoggerFactory.getLogger(RatingController.class);


	@GetMapping("/ratings/movie/{movieId}")
	public ResponseEntity<MovieRatingResponse> getAverageRatingByMovie(@PathVariable Long movieId){
		MovieRatingResponse averageRatingOfMovie = ratingService.getAverageRatingOfMovie(movieId);
		return new ResponseEntity<>(averageRatingOfMovie,HttpStatus.ACCEPTED);
	}

	@GetMapping("/ratings/user/{userId}/movie/{movieId}")
	public ResponseEntity<Double> getRatingOfUserOnMovie(@PathVariable Long userId,@PathVariable Long movieId){
		logger.info("The client demands rating of movie with movieId {} given by user with userId {}",movieId,userId);
		Double ratingOfMovieByUser = ratingService.getRatingOfMovieByUser(userId, movieId);
		logger.debug("The response rating is {}",ratingOfMovieByUser);
		return new ResponseEntity<Double>(ratingOfMovieByUser,HttpStatus.OK);
	}

	@PostMapping("/ratings/user/{userId}/movie/{movieId}")
	public ResponseEntity<ApiResponse> addOrUpdateNewRating(@Valid @RequestBody Rating rating,@PathVariable Long userId,@PathVariable Long movieId){
		ratingService.createNewRating(userId, movieId,rating);
		return new ResponseEntity<ApiResponse>(new ApiResponse("New Rating is created",true),HttpStatus.CREATED);
	}

	//controller for deleting rating of movies which get deleted from movie service database
	@DeleteMapping("/movies/ratings/deleteRating/movie/{movieId}")
	public void deleteRatingsForDeletedMovies(@PathVariable Long movieId){
		ratingService.deleteRatingsForMovie(movieId);
	}
}
