package com.project.review.services.impl;


import com.project.review.payloads.ReviewDTO;
import com.project.review.services.ReviewService;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {
    @Override
    public ReviewDTO createNewReview(ReviewDTO reviewDTO, Long userId, Long movieId) {
        return null;
    }

    @Override
    public ReviewDTO getReviewOfMovieByUser(Long userId, Long movieId) {
        return null;
    }

    @Override
    public List<ReviewDTO> getAllReviewsOfMovie(Long movieId) {
        return List.of();
    }

    @Override
    public ReviewDTO updateReview(ReviewDTO reviewDTO, Long userId, Long movieId) {
        return null;
    }

    @Override
    public void deleteReview(Long userId, Long movieId) {

    }
}
