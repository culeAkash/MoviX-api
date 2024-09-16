package com.project.movie.controllers;

import java.util.List;

import com.project.movie.payloads.MovieDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.project.movie.entities.Movie;
import com.project.movie.services.MovieService;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(MovieController.class);

	// controller for creating new movies only by admin
	@PostMapping()
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MovieDTO> createNewMovie(@Valid @RequestBody MovieDTO movie,@RequestPart(required = false) MultipartFile image){
		MovieDTO createdMovie = this.movieService.createNewMovie(movie,image);
		return new ResponseEntity<>(createdMovie,HttpStatus.CREATED);
	}

	@GetMapping("/getMovies/getAllMovies")
	public ResponseEntity<List<MovieDTO>> getAllMovies(){
		List<MovieDTO> movies = this.movieService.getAllMovies();
		return new ResponseEntity<List<MovieDTO>>(movies,HttpStatus.OK);
	}

	@GetMapping("/getMovies/getMovieById/{movieId}")
	public ResponseEntity<MovieDTO> getMovieById(@PathVariable("movieId")Long movieId){
		MovieDTO movie = movieService.getMovieById(movieId);
		return new ResponseEntity<>(movie, HttpStatus.OK);
	}

	@GetMapping("/getMovies/search/{nameDir}")
	public ResponseEntity<List<MovieDTO>> getMovieBySearchVal(@PathVariable("nameDir")String input){
		List<MovieDTO> movies = movieService.getMovieByMovieNameOrDirector(input);
		return new ResponseEntity<>(movies, HttpStatus.OK);
	}


	// delete movie controller only accessable to admin
	@DeleteMapping("/deleteMovie/{movieId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteMovie(@PathVariable("movieId")Long movieId){
		movieService.deleteMovie(movieId);
		return ResponseEntity.noContent().build();
	}

	// update movie controller only accessable to admin
	@PutMapping("/updateMovie/{movieId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MovieDTO> updateMovie(@PathVariable("movieId")Long movieId,@Valid @RequestBody MovieDTO movie,@RequestPart(required = false) MultipartFile image){
		logger.debug("The movie is to be updated has movieId {}",movieId);
		MovieDTO updatedMovie = movieService.updateMovie(movieId, movie,image);
		logger.debug("The updated movie is {}",updatedMovie);
		return new ResponseEntity<>(updatedMovie,HttpStatus.ACCEPTED);

	}

	
}