package com.project.review.services;

import com.project.review.payloads.RatingDTO;
import com.project.review.payloads.RatingResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface RatingService {

    // create new rating or update existing rating
    public RatingDTO createRating(RatingDTO ratingDTO,Long userId,Long movieId);

    // get ratings
    public RatingResponseDTO getRatingOfMovieByUser(Long userId, Long movieId);

    public RatingResponseDTO getAllRatingsOfMovie(Long movieId);

    // delete Rating of User on Movie
    public void deleteRatingByUserAndMovie(Long ratingId);


    public void deleteRatingsByMovieId(Long movieId);
}
