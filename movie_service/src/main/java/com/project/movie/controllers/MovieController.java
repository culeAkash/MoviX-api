package com.project.movie.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.movie.entities.Movie;
import com.project.movie.exceptions.ForbiddenRequestException;
import com.project.movie.payloads.FileResponse;
import com.project.movie.services.FileService;
import com.project.movie.services.MovieService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;



@RestController
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image.movies}")
	private String path;
	
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(MovieController.class);

	@PostMapping("/movies")
	public ResponseEntity<Movie> createNewMovie(@Valid @RequestBody Movie movie) throws ForbiddenRequestException{
		logger.debug("The sent movie data by client is {}",movie);
		Movie newMovie = movieService.createNewMovie(movie);
		logger.debug("The created movie is {}",newMovie);
		return new ResponseEntity<>(newMovie,HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/movies/{movieId}")
	public ResponseEntity<Movie> deleteMovie(@PathVariable("movieId")Long movieId) throws ForbiddenRequestException{
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
	public ResponseEntity<Movie> updateMovie(@PathVariable("movieId")Long movieId,@Valid @RequestBody Movie movie) throws ForbiddenRequestException{
		logger.debug("The movie is to be updated has movieId {}",movieId);
		Movie response = movieService.updateMovie(movieId, movie);
		logger.debug("The updated movie is {}",response);
		return new ResponseEntity<Movie>(response,HttpStatus.ACCEPTED);

	}

	
	
	@PostMapping("/movies/image/upload/{movieId}")
	public ResponseEntity<FileResponse<Movie>> postImage(@RequestParam("image") MultipartFile image,@PathVariable Long movieId) throws IOException, ForbiddenRequestException{
		Movie movie = this.movieService.getMovieById(movieId);
		
		String uploadImage = this.fileService.uploadImage(this.path, image);// here we will handle exception using
	
		movie.setImageUrl(uploadImage);
		
		Movie updatedMovie = this.movieService.updateMovie(movieId, movie);
		
		FileResponse<Movie> response = new FileResponse<>(updatedMovie,true);
		return new ResponseEntity<FileResponse<Movie>>(response,HttpStatus.CREATED);
	}


	@GetMapping(value = "/movies/image/{movieId}", produces = MediaType.IMAGE_JPEG_VALUE) // on firing this url in the
	// browser image will get
	// displayed
public void DownloadImage(@PathVariable Long movieId, HttpServletResponse response) throws IOException {
		Movie movieById = this.movieService.getMovieById(movieId);
		String imageUrl = movieById.getImageUrl();
		InputStream resource = this.fileService.getResource(this.path, imageUrl);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
}
	
	
	//controllers for microservice communication
	@GetMapping("/services/movies/isPresent/{movieId}")
	public Boolean checkIfMoviePresent(@PathVariable Long movieId) {
		Boolean movieById = this.movieService.getMovieByIdService(movieId);
		return movieById;
	}


}