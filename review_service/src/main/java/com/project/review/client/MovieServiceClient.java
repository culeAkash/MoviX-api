package com.project.review.client;


import com.project.review.payloads.MovieDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "movie-service",path = "/api/v1/movies")
public interface MovieServiceClient {

    @GetMapping("/getMovies/getMovieById/{movieId}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable("movieId") Long movieId);
}
