package com.project.movie.services.impl;

import java.util.List;

import com.project.movie.client.FileServiceClient;
import com.project.movie.client.ReviewServiceClient;
import com.project.movie.payloads.MovieDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.movie.entities.Movie;
import com.project.movie.exceptions.ResourceNotFoundException;
import com.project.movie.repositories.MovieRepository;
import com.project.movie.services.MovieService;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {


	private MovieRepository movieRepository;

	private ModelMapper modelMapper;

	private FileServiceClient fileServiceClient;

	private ReviewServiceClient reviewServiceClient;


	@Override
	public MovieDTO createNewMovie(MovieDTO movie, MultipartFile image) {

		if(image!=null) {
			String imageUrl = this.fileServiceClient.uploadImageToFileSystem(image).getBody();
			movie.setImageUrl(imageUrl);
		}

		Movie newMovie = modelMapper.map(movie,Movie.class);
		Movie createdMovie = this.movieRepository.save(newMovie);

		return modelMapper.map(createdMovie,MovieDTO.class);
	}

	@Override
	public List<MovieDTO> getAllMovies() {
		List<Movie> allMovies = this.movieRepository.findAll();

		return allMovies.stream().map(movie -> {
			MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);
			movieDTO.setRatingResponse(this.reviewServiceClient.getAverageRating(movieDTO.getMovieId()));
			return movieDTO;
		}).toList();
	}


	@Override
	public MovieDTO getMovieById(Long movieId) {
		Movie movieById = this.movieRepository.findById(movieId)
				.orElseThrow(() -> new ResourceNotFoundException("Movie","Movie ID",movieId));



		MovieDTO movieDTO =  modelMapper.map(movieById,MovieDTO.class);
		movieDTO.setRatingResponse(this.reviewServiceClient.getAverageRating(movieId));

		return movieDTO;
	}


	@Override
	public List<MovieDTO> getMovieByMovieNameOrDirector(String directorOrMovieName) {
		List<Movie> movies = movieRepository.findMovieByNameOrDirector(directorOrMovieName);

		if (movies.isEmpty()) {
			throw new ResourceNotFoundException("Movie", "field", directorOrMovieName);
		}

		return movies.stream().map(movie -> {
			MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);
			movieDTO.setRatingResponse(this.reviewServiceClient.getAverageRating(movieDTO.getMovieId()));
			return movieDTO;
		}).toList();

	}



	@Override
	public void deleteMovie(Long movieId) {
		Movie deletedMovie = movieRepository.findById(movieId)
				.orElseThrow(()->new ResourceNotFoundException("Movie", "movieId", movieId));

		this.fileServiceClient.deleteImageFromFileSystem(deletedMovie.getImageUrl());
		this.reviewServiceClient.deleteRatingsForMovie(movieId);
		this.reviewServiceClient.deleteReviewsForMovie(movieId);


		this.movieRepository.delete(deletedMovie);
    }

	@Override
	public MovieDTO updateMovie(Long movieId, MovieDTO movie,MultipartFile image) {
		Movie toUpdateMovie = movieRepository.findById(movieId)
				.orElseThrow(() -> new ResourceNotFoundException("Movie", "movieId", movieId));

		if(movie.getMovieName()!=null)
			toUpdateMovie.setMovieName(movie.getMovieName());
		if(movie.getDirector()!=null)
			toUpdateMovie.setDirector(movie.getDirector());
		if(movie.getSynopsis()!=null)
			toUpdateMovie.setSynopsis(movie.getSynopsis());
		if(movie.getImageUrl()!=null)
			toUpdateMovie.setImageUrl(movie.getImageUrl());
		if(movie.getVideoUrl()!=null)
			toUpdateMovie.setVideoUrl(movie.getVideoUrl());

		if(image!=null) {
			this.fileServiceClient.deleteImageFromFileSystem(toUpdateMovie.getImageUrl());
			String imageUrl = this.fileServiceClient.uploadImageToFileSystem(image).getBody();
			toUpdateMovie.setImageUrl(imageUrl);
		}

		movieRepository.save(toUpdateMovie);
		return modelMapper.map(toUpdateMovie,MovieDTO.class);

	}

}

