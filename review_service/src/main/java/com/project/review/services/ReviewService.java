package com.project.review.services;

import com.project.review.payloads.ReviewDTO;
import com.project.review.payloads.ReviewResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

    public ReviewDTO createNewReview(ReviewDTO reviewDTO,Long userId,Long movieId);

    public ReviewResponseDTO getReviewOfMovieByUser(Long userId, Long movieId);

    public List<ReviewResponseDTO> getAllReviewsOfMovie(Long movieId);

    public ReviewDTO updateReview(ReviewDTO reviewDTO,Long userId,Long movieId);

    public void deleteReview(Long userId,Long movieId);
}
