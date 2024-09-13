package com.project.review.services.impl;


import com.project.review.client.MovieServiceClient;
import com.project.review.client.UserServiceClient;
import com.project.review.entities.Review;
import com.project.review.exceptions.GenericException;
import com.project.review.payloads.MovieDTO;
import com.project.review.payloads.ReviewDTO;
import com.project.review.payloads.ReviewResponseDTO;
import com.project.review.payloads.UserDTO;
import com.project.review.repositories.ReviewRepository;
import com.project.review.services.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.project.review.utils.AccessUtils.validateMovieExistence;
import static com.project.review.utils.AccessUtils.validateUserAccess;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private UserServiceClient userServiceClient;


    @Autowired
    private MovieServiceClient movieServiceClient;


    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ReviewDTO createNewReview(ReviewDTO reviewDTO, Long userId, Long movieId) {
        UserDTO userDTO = validateUserAccess(userId);
        MovieDTO movieDTO = validateMovieExistence(movieId);

        Review newReview = Review.builder()
                .reviewContent(reviewDTO.getReviewContent())
                .userId(userDTO.getUserId())
                .movieId(movieDTO.getMovieId())
                .build();

        Review savedReview = this.reviewRepository.save(newReview);

        return modelMapper.map(savedReview,ReviewDTO.class);
    }

    @Override
    public ReviewResponseDTO getReviewOfMovieByUser(Long userId, Long movieId) {
        UserDTO userDTO = validateUserAccess(userId);
        MovieDTO movieDTO = validateMovieExistence(movieId);

        Review reviewByUserAndMovie = this.reviewRepository.findByMovieIdAndUserId(movieDTO.getMovieId(),userDTO.getUserId())
                .orElseThrow(()-> new GenericException("No review is submitted by given user to the movie", HttpStatus.NOT_FOUND));


        return ReviewResponseDTO.builder()
                .reviewId(reviewByUserAndMovie.getReviewId())
                .reviewContent(reviewByUserAndMovie.getReviewContent())
                .createdAt(reviewByUserAndMovie.getCreatedAt())
                .updatedAt(reviewByUserAndMovie.getUpdatedAt())
                .userDTO(userDTO)
                .build();

    }

    @Override
    public List<ReviewResponseDTO> getAllReviewsOfMovie(Long movieId) {
        validateMovieExistence(movieId);

        List<Review> reviewsOfMovie  = this.reviewRepository.findByMovieId(movieId);

        return reviewsOfMovie.stream()
                .map(review -> this.modelMapper.map(review,ReviewResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO updateReview(ReviewDTO reviewDTO, Long userId, Long movieId) {
        validateMovieExistence(movieId);
        validateUserAccess(userId);

        if(!Objects.equals(reviewDTO.getUserId(), userId)){
            throw new GenericException("User is not authorized to update the review", HttpStatus.UNAUTHORIZED);
        }

        Review toUpdateReview = this.reviewRepository.findByMovieIdAndUserId(movieId,userId)
                .orElseThrow(()-> new GenericException("No review is submitted by given user to the movie", HttpStatus.NOT_FOUND));

        toUpdateReview.setReviewContent(reviewDTO.getReviewContent());

        Review updatedReview = this.reviewRepository.save(toUpdateReview);

        return this.modelMapper.map(updatedReview,ReviewDTO.class);
    }

    @Override
    public void deleteReview(Long userId, Long movieId) {
        validateMovieExistence(movieId);
        validateUserAccess(userId);

        // check if the user is ADMIN or USER
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Extracting roles
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Review reviewByUserForMovie = this.reviewRepository.findByMovieIdAndUserId(movieId,userId)
                .orElseThrow(()-> new GenericException("No review is submitted by given user to the movie", HttpStatus.NOT_FOUND));

        if(roles.contains("ROLE_ADMIN")){
            this.reviewRepository.delete(reviewByUserForMovie);
        }
        else if(roles.contains("ROLE_USER") && Objects.equals(userId, reviewByUserForMovie.getUserId())) {
            this.reviewRepository.delete(reviewByUserForMovie);
        }
        else{
            throw new GenericException("User is not authorized to delete the review", HttpStatus.UNAUTHORIZED);
        }


        }

}
