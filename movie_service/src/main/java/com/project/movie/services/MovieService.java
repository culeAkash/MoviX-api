package com.project.movie.services;

import java.util.List;

import com.project.movie.entities.Movie;

public interface MovieService {


	public Movie createNewMovie(Movie movie);

	public Movie deleteMovie(Long movieId);

	public List<Movie> getAllMovies();

	public Movie getMovieById(Long movieId);

	public Movie updateMovie(Long movieId,Movie movie);

	public Boolean getMovieByIdService(Long movieId);

	List<Movie> getMovieByMovieNameOrDirector(String movieName);
}
