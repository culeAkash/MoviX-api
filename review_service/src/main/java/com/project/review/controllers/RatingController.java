package com.project.review.controllers;

import com.project.review.payloads.RatingDTO;
import com.project.review.payloads.RatingResponseDTO;
import com.project.review.services.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
@AllArgsConstructor
public class RatingController {


    private RatingService ratingService;

    @PostMapping("/createRating/user/{userId}/movie/{movieId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RatingDTO> createRating(@RequestBody RatingDTO ratingDTO,@PathVariable("userId")Long userId, @PathVariable("movieId") Long movieId){
        RatingDTO newRatingDTO = this.ratingService.createRating(ratingDTO,userId,movieId);
        return new ResponseEntity<>(newRatingDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getAverageRating/movie/{movieId}")
    public ResponseEntity<RatingResponseDTO> getAllRatingsOfMovie(@PathVariable("movieId") Long movieId){
        RatingResponseDTO averageRatingOfMovie =  this.ratingService.getAllRatingsOfMovie(movieId);
        return ResponseEntity.ok().body(averageRatingOfMovie);
    }

    @GetMapping("/getRating/user/{userId}/movie/{movieId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RatingResponseDTO> getRatingByUserIdAndMovieId(@PathVariable("userId") Long userId,@PathVariable("movieId") Long movieId){
        RatingResponseDTO ratingResponseDTO = this.ratingService.getRatingOfMovieByUser(userId,movieId);
        return ResponseEntity.ok().body(ratingResponseDTO);
    }



    @DeleteMapping("/deleteRating/{ratingId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteRatingByUserAndMovie(@PathVariable("ratingId") Long ratingId){
        this.ratingService.deleteRatingByUserAndMovie(ratingId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/deleteRatingsForMovie/movie/{movieId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRatingsByMovieId(@PathVariable("movieId") Long movieId){
        this.ratingService.deleteRatingsByMovieId(movieId);
        return ResponseEntity.noContent().build();
    }

}
