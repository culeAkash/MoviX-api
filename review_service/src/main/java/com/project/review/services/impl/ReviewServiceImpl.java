package com.project.review.services.impl;


import com.project.review.client.MovieServiceClient;
import com.project.review.client.UserServiceClient;
import com.project.review.entities.Review;
import com.project.review.payloads.MovieDTO;
import com.project.review.payloads.ReviewDTO;
import com.project.review.payloads.UserDTO;
import com.project.review.repositories.ReviewRepository;
import com.project.review.services.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
