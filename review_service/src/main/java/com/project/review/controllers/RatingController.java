package com.project.review.controllers;

import com.project.review.payloads.RatingDTO;
import com.project.review.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/createRating/user/{userId}/movie/{movieId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RatingDTO> createRating(@RequestBody RatingDTO ratingDTO,@PathVariable("userId")Long userId, @PathVariable("movieId") Long movieId){
        RatingDTO newRatingDTO = this.ratingService.createRating(ratingDTO,userId,movieId);
        return new ResponseEntity<>(newRatingDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getRating/user/{userId}/movie/{movieId}")
    public ResponseEntity<RatingDTO> getRatingByUserIdAndMovieId(@PathVariable("userId") Long userId,@PathVariable("movieId") Long movieId){
        return null;
    }

    @GetMapping("/getAllRatings/movie/{movieId}")
    public ResponseEntity<List<RatingDTO>> getAllRatingsOfMovie(@PathVariable("movieId") Long movieId){
        return null;
    }

    @DeleteMapping("/deleteRating/user/{userId}/movie/{movieId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteRatingByUserAndMovie(@PathVariable("userId") Long userId,@PathVariable("movieId") Long movieId){
        return null;
    }

}
