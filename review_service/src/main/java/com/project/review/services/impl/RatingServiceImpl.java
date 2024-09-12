package com.project.review.services.impl;

import com.project.review.client.MovieServiceClient;
import com.project.review.client.UserServiceClient;
import com.project.review.entities.Rating;
import com.project.review.payloads.MovieDTO;
import com.project.review.payloads.RatingDTO;
import com.project.review.payloads.RatingResponseDTO;
import com.project.review.payloads.UserDTO;
import com.project.review.repositories.RatingRepository;
import com.project.review.services.RatingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


import static com.project.review.utils.AccessUtils.validateMovieExistence;
import static com.project.review.utils.AccessUtils.validateUserAccess;

@Service
public class RatingServiceImpl implements RatingService {


    @Autowired
    private UserServiceClient userServiceClient;


    @Autowired
    private MovieServiceClient movieServiceClient;


    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public RatingDTO createRating(RatingDTO ratingDTO, Long userId, Long movieId) {
        UserDTO userDTO = validateUserAccess(userId);
        MovieDTO movieDTO = validateMovieExistence(movieId);

        Rating newRating = Rating
                .builder()
                .ratingValue(ratingDTO.getRatingValue())
                .movieId(movieDTO.getMovieId())
                .userId(userDTO.getUserId())
                .build();

        Rating rating = this.ratingRepository.save(newRating);

        return modelMapper.map(rating, RatingDTO.class);
    }


    @Override
    public RatingResponseDTO getAllRatingsOfMovie(Long movieId) {
        List<Rating> ratingsByMovie = this.ratingRepository.findByMovieId(movieId);

        //average all the ratings to find the average rating of the movie
        Double averageRating = ratingsByMovie.stream()
                .mapToDouble(Rating::getRatingValue)
                .average()
                .orElse(0.0);

        return RatingResponseDTO.builder()
                .ratingValue(averageRating)
                .userRatedCount(ratingsByMovie.size())
                .build();
    }

    @Override
    public RatingResponseDTO getRatingOfMovieByUser(Long userId, Long movieId) {
        validateUserAccess(userId);
        validateMovieExistence(movieId);

        Rating ratingByUserAndMovie = this.ratingRepository.findByMovieIdAndUserId(movieId,userId);


        return RatingResponseDTO.builder()
                .ratingValue(Double.valueOf(ratingByUserAndMovie.getRatingValue()))
                .userRatedCount(1)
                .build();
    }



    @Override
    public void deleteRatingByUserAndMovie(Long userId, Long movieId) {
        validateUserAccess(userId);
        validateMovieExistence(movieId);

        Rating ratingByUserAndMovie = this.ratingRepository.findByMovieIdAndUserId(movieId,userId);

        if(ratingByUserAndMovie!=null){
            this.ratingRepository.delete(ratingByUserAndMovie);
        }
    }



}
