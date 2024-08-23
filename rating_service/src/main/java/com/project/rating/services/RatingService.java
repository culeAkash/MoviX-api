package com.project.rating.services;

import com.project.rating.entities.Rating;
import com.project.rating.payloads.MovieRatingResponse;

public interface RatingService {

	public MovieRatingResponse getAverageRatingOfMovie(Long movieId);
	
	public Double getRatingOfMovieByUser(Long userId,Long movieId);
	
	public void createNewRating(Long userId,Long movieId,Rating rating);
	
	public Rating updateRating(Long userId,Long movieId);
	
	public void deleteRatingsForMovie(Long movieId);
}
