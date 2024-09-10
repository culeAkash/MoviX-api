package com.project.review.controllers;

import com.project.review.payloads.ReviewDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @PostMapping("/createNewReview/user/{userId}/movie/{movieId}")
    public ResponseEntity<ReviewDTO> createNewReview(@Valid @RequestBody ReviewDTO reviewDTO, @PathVariable("userId") Long userId, @PathVariable("movieId") Long movieId){
        return null;
    }

    @GetMapping("/getReviews/user/{userId}/movie/{movieId}")
    public ResponseEntity<ReviewDTO> getReviewOfMovieByUser(@PathVariable("userId") Long userId,@PathVariable("movieId") Long movieId){
        return null;
    }

    @GetMapping("/getAllReviews/movie/{movieId}")
    public ResponseEntity<List<ReviewDTO>> getAllReviewsOfMovie(@PathVariable("movieId") Long movieId){
        return null;
    }

    @PutMapping("/updateReview/user/{userId}/movie/{movieId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReviewDTO> updateReview(@RequestBody ReviewDTO reviewDTO, @PathVariable("userId") Long userId,@PathVariable("movieId")Long movieId){
        return null;
    }

    @DeleteMapping("/deleteReview/user/{userId}/movie/{movieId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Void> deleteReview(@PathVariable("userId") Long userId,@PathVariable("movieId") Long movieId){
        return null;
    }


}
