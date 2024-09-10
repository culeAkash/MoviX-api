package com.project.review.services;

import com.project.review.payloads.RatingDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {

    // create new rating or update existing rating
    public RatingDTO createRating(RatingDTO ratingDTO,Long userId,Long movieId);

    // get ratings
    public RatingDTO getRatingOfMovieByUser(Long userId,Long movieId);

    public List<RatingDTO> getAllRatingsOfMovie(Long movieId);

    // delete Rating of User on Movie
    public void deleteRatingByUserAndMovie(Long userId,Long movieId);
}
