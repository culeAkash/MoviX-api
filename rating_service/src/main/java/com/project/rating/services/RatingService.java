package com.project.rating.services;

import java.util.List;

import com.project.rating.entities.Rating;
import com.project.rating.exceptions.ForbiddenRequestException;
import com.project.rating.payloads.MovieRatingResponse;

public interface RatingService {

	public MovieRatingResponse getAverageRatingOfMovie(Long movieId);
	
	public Double getRatingOfMovieByUser(Long userId,Long movieId) throws ForbiddenRequestException;
	
	public void createNewRating(Long userId,Long movieId,Rating rating) throws ForbiddenRequestException;
	
	public Rating updateRating(Long userId,Long movieId);
	
	public void deleteRatingsForMovie(Long movieId);
}
