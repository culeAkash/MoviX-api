package com.project.movie.services;

import java.util.List;

import com.project.movie.entities.Movie;
import com.project.movie.payloads.MovieDTO;

public interface MovieService {


	public MovieDTO createNewMovie(MovieDTO movie);

	public List<MovieDTO> getAllMovies();

	public MovieDTO getMovieById(Long movieId);

	List<MovieDTO> getMovieByMovieNameOrDirector(String directorOrMovieName);

	public void deleteMovie(Long movieId);

	public MovieDTO updateMovie(Long movieId,MovieDTO movie);
}
