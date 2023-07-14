package com.project.movie.services.impl;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.weaver.loadtime.Options;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.netflix.discovery.converters.Auto;
import com.project.movie.entities.Movie;
import com.project.movie.exceptions.ForbiddenRequestException;
import com.project.movie.exceptions.ResourceNotFoundException;
import com.project.movie.payloads.RatingDto;
import com.project.movie.repositories.MovieRepository;
import com.project.movie.services.MovieService;

@Service
public class MovieServiceImpl implements MovieService {
	
	Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

	@Autowired
	private MovieRepository movieRepository;

	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ModelMapper modelMapper;


	@Override
	public Movie createNewMovie(Movie movie) throws ForbiddenRequestException {
		//only admin can create a movie after login
		if(checkUserRoleFromUserService()) {
			logger.warn("Only admin is allowed to access this functionality");
			throw new ForbiddenRequestException("admin");}
		
		Movie save = movieRepository.save(movie);
		return save;
	}

	@Override
	public Movie deleteMovie(Long movieId) throws ForbiddenRequestException {
		//only admin can create a movie after login
				if(checkUserRoleFromUserService())
					throw new ForbiddenRequestException("admin");
		Movie req = movieRepository.findById(movieId)
				.orElseThrow(()->new ResourceNotFoundException("Movie", "movieId", movieId));
		
		this.restTemplate.delete("http://rating-service/movies/ratings/deleteRating/movie/"+req.getMovieId());
		movieRepository.delete(req);
		return req;
	}

	@Override
	public List<Movie> getAllMovies() {
		List<Movie> allMovies = movieRepository.findAll();
		List<Movie> response = new ArrayList<>();
		for(Movie movie : allMovies) {
			Long movieId = movie.getMovieId();
			RatingDto rating = this.getRatingDetailsFromRatingService(movieId);
			movie.setRating(rating);
			response.add(movie);
		}
		return response;
	}

	@Override
	public Movie getMovieById(Long movieId) {
		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(()->new ResourceNotFoundException("Movie", "movieId", movieId));
		
		RatingDto rating = this.getRatingDetailsFromRatingService(movie.getMovieId());
		movie.setRating(rating);
		return movie;
	}

	@Override
	public List<Movie> getMovieByMovieNameOrDirector(String input) {
		List<Movie> movies = movieRepository.findMovieByNameOrDirector(input);

//		if(movies.size()==0) {
//			throw new ResourceNotFoundException("Movie", "field", input);
//		}
		List<Movie> response = new ArrayList<>();
		for(Movie movie : movies) {
			Long movieId = movie.getMovieId();
			RatingDto rating = this.getRatingDetailsFromRatingService(movieId);
			movie.setRating(rating);
			response.add(movie);
		}

		return response;
	}

	@Override
	public Movie updateMovie(Long movieId, Movie movie) throws ForbiddenRequestException {
		//only admin can create a movie after login
				if(checkUserRoleFromUserService())
					throw new ForbiddenRequestException("admin");
		Movie updateMovie = movieRepository.findById(movieId)
		.orElseThrow(()->new ResourceNotFoundException("Movie", "movieId", movieId));

		movie.setMovieId(movieId);
		movie.setImageUrl(updateMovie.getImageUrl());
		movieRepository.save(movie);
		return movie;

	}
	
	
	public Boolean checkUserRoleFromUserService() {
		String role = this.restTemplate.getForObject("http://user-service/services/users/getLoggedIn/role", String.class);
		return !role.equals("admin");
	}

	@Override
	public Boolean getMovieByIdService(Long movieId) {
		Movie movie = this.movieRepository.findById(movieId).orElse(null);
		return movie!=null;
	}

	
	public RatingDto getRatingDetailsFromRatingService(Long movieId) {
		Object ratingObject = this.restTemplate.getForObject("http://rating-service/ratings/movie/"+movieId, Object.class);
		RatingDto rating = this.modelMapper.map(ratingObject, RatingDto.class);
		return rating;
	}
}

