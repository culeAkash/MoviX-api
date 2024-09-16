package com.project.movie.client;

import com.project.movie.payloads.RatingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "review-service",path = "/api/v1/reviews/")
public interface ReviewServiceClient {
    @DeleteMapping(value = "/deleteReviewsForMovie/movie/{movieId}")
    void deleteReviewsForMovie(@PathVariable Long movieId);

    @DeleteMapping(value = "/deleteRatingsForMovie/movie/{movieId}")
    void deleteRatingsForMovie(@PathVariable Long movieId);

    @GetMapping(value = "/getAverageRating/movie/{movieId}")
    RatingResponse getAverageRating(@PathVariable Long movieId);
}
