package com.project.review.services.impl;

import com.project.review.client.MovieServiceClient;
import com.project.review.client.UserServiceClient;
import com.project.review.entities.Rating;
import com.project.review.exceptions.GenericException;
import com.project.review.payloads.MovieDTO;
import com.project.review.payloads.RatingDTO;
import com.project.review.payloads.UserDTO;
import com.project.review.repositories.RatingRepository;
import com.project.review.services.RatingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object email = authentication.getPrincipal();

        UserDTO userDTO = userServiceClient.getUserByUserEmail(String.valueOf(email)).getBody();

        if (userDTO == null) {
            throw new GenericException("User not found", HttpStatus.NOT_FOUND);
        }

        if(!Objects.equals(userDTO.getUserId(), userId)){
            throw new GenericException("Request not permitted", HttpStatus.FORBIDDEN);
        }

        MovieDTO movieDTO = this.movieServiceClient.getMovieById(movieId).getBody();

        if (movieDTO == null) {
            throw new GenericException("Movie not found", HttpStatus.NOT_FOUND);
        }

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
