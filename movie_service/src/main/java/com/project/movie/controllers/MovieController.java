package com.project.movie.controllers;

import java.util.List;

import com.project.movie.payloads.MovieDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.movie.entities.Movie;
import com.project.movie.services.MovieService;

import jakarta.validation.Valid;



@RestController
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(MovieController.class);

	@PostMapping("/movies")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MovieDTO> createNewMovie(@Valid @RequestBody MovieDTO movie){
		MovieDTO createdMovie = this.movieService.createNewMovie(movie);
		return new ResponseEntity<>(createdMovie,HttpStatus.CREATED);
	}

	@DeleteMapping("/movies/{movieId}")
	public ResponseEntity<Movie> deleteMovie(@PathVariable("movieId")Long movieId){
		Movie res = movieService.deleteMovie(movieId);
		return new ResponseEntity<Movie>(res,HttpStatus.ACCEPTED);
	}

	@GetMapping("/movies")
	public ResponseEntity<List<Movie>> getAllMovies(){
		List<Movie> movies = movieService.getAllMovies();
		return new ResponseEntity<List<Movie>>(movies,HttpStatus.OK);
	}


	@GetMapping("/movies/search/{nameDir}")
	public ResponseEntity<List<Movie>> getMovieBySearchVal(@PathVariable("nameDir")String input){
		List<Movie> movies = movieService.getMovieByMovieNameOrDirector(input);
		return new ResponseEntity<List<Movie>>(movies,HttpStatus.OK);
	}


	@GetMapping("/movies/id/{movieId}")
	public ResponseEntity<Movie> getMovieById(@PathVariable("movieId")Long movieId){
		Movie movie = movieService.getMovieById(movieId);
		return new ResponseEntity<Movie>(movie,HttpStatus.OK);
	}



	@PutMapping("/movies/{movieId}")
	public ResponseEntity<Movie> updateMovie(@PathVariable("movieId")Long movieId,@Valid @RequestBody Movie movie){
		logger.debug("The movie is to be updated has movieId {}",movieId);
		Movie response = movieService.updateMovie(movieId, movie);
		logger.debug("The updated movie is {}",response);
		return new ResponseEntity<Movie>(response,HttpStatus.ACCEPTED);

	}

	//controllers for microservice communication
	@GetMapping("/services/movies/isPresent/{movieId}")
	public Boolean checkIfMoviePresent(@PathVariable Long movieId) {
		Boolean movieById = this.movieService.getMovieByIdService(movieId);
		return movieById;
	}


}