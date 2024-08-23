package com.project.movie.services;

import java.util.List;

import com.project.movie.entities.Review;

public interface ReviewService {

	
public List<Review> getAllReviewsOfMovie(Long movieId);
	
	public Review updateReview(Long reviewId,Review review);
	
	public void deleteReview(Long reviewId);
	
	public Review createReview(Review review,Long movieId,Long userId);
	
	public List<Review> getAllReviewsByUserId(Long userId);
}
