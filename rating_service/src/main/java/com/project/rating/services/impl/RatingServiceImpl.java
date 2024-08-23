package com.project.rating.services.impl;

import java.text.DecimalFormat;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.rating.entities.Rating;
import com.project.rating.exceptions.ResourceNotFoundException;
import com.project.rating.payloads.MovieRatingResponse;
import com.project.rating.repositories.RatingRepository;
import com.project.rating.services.RatingService;

@Service
public class RatingServiceImpl implements RatingService{

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RatingRepository ratingRepository;

	private org.slf4j.Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);

	@Override
	public MovieRatingResponse getAverageRatingOfMovie(Long movieId) {
		//check if movie is present or not in the database
		//we have to check it from another microservice
		Boolean isMoviePresent = restTemplate.getForObject("http://movie-service/services/movies/isPresent/"+movieId, Boolean.class);
		Double averageRating = 0.0;
		if(!isMoviePresent) {
			//throw new Movie Not FOund Exception
			throw new ResourceNotFoundException("Movie", "movieId", movieId);
		}
		List<Rating> findRatingsByMovie = ratingRepository.findRatingsByMovie(movieId);
		long count = findRatingsByMovie.size();
		for(Rating r : findRatingsByMovie) {
			averageRating += r.getRating();
		}

		logger.debug("The sum rating before averaging is {}",averageRating);

		averageRating = averageRating*1.0/count;
		if(count==0) {
			averageRating = 0.0;
		}

		logger.debug("The averaged rating is {}",averageRating);

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(1);
		String ratingString = df.format(averageRating);
		averageRating = Double.parseDouble(ratingString);

		logger.debug("The rating with precision upto one decimal place is {}",averageRating);

		return new MovieRatingResponse(averageRating,count);

	}

	@Override
	public Double getRatingOfMovieByUser(Long userId, Long movieId){


		//first check if user is present
		Boolean isUserPresent = restTemplate.getForObject("http://user-service/services/users/confirmUserPresence/"+userId, Boolean.class);
		if(!isUserPresent) {
			throw new ResourceNotFoundException("User", "userId", userId);
		}

		Boolean isMoviePresent = restTemplate.getForObject("http://movie-service/services/movies/isPresent/"+movieId, Boolean.class);
		if(!isMoviePresent) {
			//throw new Movie Not FOund Exception
			throw new ResourceNotFoundException("Movie", "movieId", movieId);
		}

		Rating rating = ratingRepository.findByMovieAndUser(userId, movieId);
		if(rating==null) {
			throw new ResourceNotFoundException("Rating", "userId with movieId", String.format("movieId : %s and userId : %s", movieId,userId));
		}

		return rating.getRating();

	}

	@Override
	public void createNewRating(Long userId, Long movieId,Rating rating){
		//first check if user is present
		Boolean isUserPresent = restTemplate.getForObject("http://user-service/services/users/confirmUserPresence/"+userId, Boolean.class);
		if(!isUserPresent) {
			throw new ResourceNotFoundException("User", "userId", userId);
		}

		Boolean isMoviePresent = restTemplate.getForObject("http://movie-service/services/movies/isPresent/"+movieId, Boolean.class);
		if(!isMoviePresent) {
			//throw new Movie Not FOund Exception
			throw new ResourceNotFoundException("Movie", "movieId", movieId);
		}

		//check if user has already rated the movie
		Rating ratingByUser = ratingRepository.findByMovieAndUser(userId, movieId);

		//if not rated create new rating object
		if(ratingByUser==null) {
			rating.setMovieId(movieId);
			rating.setUserId(userId);
			ratingRepository.save(rating);
		}
		else {
			ratingByUser.setRating(rating.getRating());
			ratingRepository.save(ratingByUser);
		}


	}


	@Override
	public Rating updateRating(Long userId, Long movieId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRatingsForMovie(Long movieId) {
		List<Rating> findRatingsByMovie = ratingRepository.findRatingsByMovie(movieId);
		for(Rating rating:findRatingsByMovie ) {
			ratingRepository.delete(rating);
		}
	}



}
