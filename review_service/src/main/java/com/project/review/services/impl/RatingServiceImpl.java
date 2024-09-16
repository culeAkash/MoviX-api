package com.project.review.services.impl;

import com.project.review.entities.Rating;
import com.project.review.exceptions.GenericException;
import com.project.review.payloads.MovieDTO;
import com.project.review.payloads.RatingDTO;
import com.project.review.payloads.RatingResponseDTO;
import com.project.review.payloads.UserDTO;
import com.project.review.repositories.RatingRepository;
import com.project.review.services.RatingService;
import com.project.review.utils.AccessUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@AllArgsConstructor
@NoArgsConstructor
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;
    private ModelMapper modelMapper;
    private AccessUtils accessUtils;


    @Override
    public RatingDTO createRating(RatingDTO ratingDTO, Long userId, Long movieId) {
        UserDTO userDTO = accessUtils.validateUserAccess(userId);
        MovieDTO movieDTO = accessUtils.validateMovieExistence(movieId);

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
        accessUtils.validateUserAccess(userId);
        accessUtils.validateMovieExistence(movieId);

        Rating ratingByUserAndMovie = this.ratingRepository.findByMovieIdAndUserId(movieId,userId)
                .orElseThrow(()-> new GenericException("No rating is submitted by given user to the movie", HttpStatus.NOT_FOUND));


        return RatingResponseDTO.builder()
                .ratingValue(Double.valueOf(ratingByUserAndMovie.getRatingValue()))
                .userRatedCount(1)
                .build();
    }



    @Override
    public void deleteRatingByUserAndMovie(Long ratingId) {

        Rating toDeleteRating = this.ratingRepository.findById(ratingId)
                .orElseThrow(()->new GenericException("No rating is found for the given Id", HttpStatus.NOT_FOUND));


        accessUtils.validateUserAccess(toDeleteRating.getUserId());

        this.ratingRepository.delete(toDeleteRating);
    }

    @Override
    public void deleteRatingsByMovieId(Long movieId) {
        this.ratingRepository.deleteAllByMovieId(movieId);
    }


}
