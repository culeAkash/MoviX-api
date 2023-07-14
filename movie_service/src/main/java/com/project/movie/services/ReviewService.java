package com.project.movie.services;

import java.util.List;

import com.project.movie.entities.Review;
import com.project.movie.exceptions.ForbiddenRequestException;

public interface ReviewService {

	
public List<Review> getAllReviewsOfMovie(Long movieId);
	
	public Review updateReview(Long reviewId,Review review) throws ForbiddenRequestException;
	
	public void deleteReview(Long reviewId) throws ForbiddenRequestException;
	
	public Review createReview(Review review,Long movieId,Long userId) throws ForbiddenRequestException;
	
	public List<Review> getAllReviewsByUserId(Long userId);
}
