package com.project.movie.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.movie.entities.Movie;
import com.project.movie.exceptions.ForbiddenRequestException;

public interface MovieService {


	public Movie createNewMovie(Movie movie) throws ForbiddenRequestException;

	public Movie deleteMovie(Long movieId) throws ForbiddenRequestException;

	public List<Movie> getAllMovies();

	public Movie getMovieById(Long movieId);

	public Movie updateMovie(Long movieId,Movie movie) throws ForbiddenRequestException;

	public Boolean getMovieByIdService(Long movieId);

	List<Movie> getMovieByMovieNameOrDirector(String movieName);
}
