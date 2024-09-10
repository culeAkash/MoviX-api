package com.project.review.services.impl;

import com.project.review.payloads.RatingDTO;
import com.project.review.services.RatingService;

import java.util.List;

public class RatingServiceImpl implements RatingService {
    @Override
    public RatingDTO createRating(RatingDTO ratingDTO, Long userId, Long movieId) {
        return null;
    }

    @Override
    public RatingDTO getRatingOfMovieByUser(Long userId, Long movieId) {
        return null;
    }

    @Override
    public List<RatingDTO> getAllRatingsOfMovie(Long movieId) {
        return List.of();
    }

    @Override
    public void deleteRatingByUserAndMovie(Long userId, Long movieId) {

    }
}
