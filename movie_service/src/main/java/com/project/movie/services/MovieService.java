package com.project.movie.services;

import java.util.List;

import com.project.movie.entities.Movie;
import com.project.movie.payloads.MovieDTO;

public interface MovieService {


	public MovieDTO createNewMovie(MovieDTO movie);

	public Movie deleteMovie(Long movieId);

	public List<Movie> getAllMovies();

	public Movie getMovieById(Long movieId);

	public Movie updateMovie(Long movieId,Movie movie);

	public Boolean getMovieByIdService(Long movieId);

	List<Movie> getMovieByMovieNameOrDirector(String movieName);
}
