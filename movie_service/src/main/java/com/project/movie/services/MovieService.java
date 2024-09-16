package com.project.movie.services;

import java.util.List;

import com.project.movie.entities.Movie;
import com.project.movie.payloads.MovieDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MovieService {


	public MovieDTO createNewMovie(MovieDTO movie, MultipartFile image);

	public List<MovieDTO> getAllMovies();

	public MovieDTO getMovieById(Long movieId);

	List<MovieDTO> getMovieByMovieNameOrDirector(String directorOrMovieName);

	public void deleteMovie(Long movieId);

	public MovieDTO updateMovie(Long movieId,MovieDTO movie,MultipartFile image);
}
