package com.project.review.controllers;

import com.project.review.payloads.ReviewDTO;
import com.project.review.payloads.ReviewResponseDTO;
import com.project.review.repositories.ReviewRepository;
import com.project.review.services.ReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@AllArgsConstructor
@NoArgsConstructor
public class ReviewController {

    private ReviewService reviewService;

    @PostMapping("/createNewReview/user/{userId}/movie/{movieId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReviewDTO> createNewReview(@Valid @RequestBody ReviewDTO reviewDTO, @PathVariable("userId") Long userId, @PathVariable("movieId") Long movieId){
        ReviewDTO newReviewDTO = this.reviewService.createNewReview(reviewDTO,userId,movieId);
        return new ResponseEntity<>(newReviewDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getReviews/user/{userId}/movie/{movieId}")
    public ResponseEntity<ReviewResponseDTO> getReviewOfMovieByUser(@PathVariable("userId") Long userId, @PathVariable("movieId") Long movieId){
        ReviewResponseDTO reviewResponseDTO = this.reviewService.getReviewOfMovieByUser(userId,movieId);
        return ResponseEntity.ok().body(reviewResponseDTO);
    }

    @GetMapping("/getAllReviews/movie/{movieId}")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviewsOfMovie(@PathVariable("movieId") Long movieId){
        List<ReviewResponseDTO> reviewResponseDTOS = this.reviewService.getAllReviewsOfMovie(movieId);
        return ResponseEntity.ok().body(reviewResponseDTOS);
    }

    @PutMapping("/updateReview/{reviewId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReviewDTO> updateReview(@RequestBody ReviewDTO reviewDTO, @PathVariable("reviewId") Long reviewId){
        ReviewDTO updatedReviewDTO = this.reviewService.updateReview(reviewDTO,reviewId);
        return new ResponseEntity<>(updatedReviewDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deleteReview/{reviewId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") Long reviewId){
        this.reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/deleteReviewsForMovie/movie/{movieId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteReviewsByMovieId(@PathVariable("movieId") Long movieId){
        this.reviewService.deleteReviewsByMovieId(movieId);
        return ResponseEntity.noContent().build();
    }


}
