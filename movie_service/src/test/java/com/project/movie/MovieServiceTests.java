package com.project.movie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.project.movie.entities.Movie;
import com.project.movie.payloads.RatingDto;
import com.project.movie.repositories.MovieRepository;
import com.project.movie.services.impl.MovieServiceImpl;

@SpringBootTest(classes= {MovieServiceTests.class})
public class MovieServiceTests {

	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private MovieRepository movieRepository;
	
	@InjectMocks
	private MovieServiceImpl movieService;
	
	
	List<Movie> movies = new ArrayList<>();
	
	@Mock
	ModelMapper mapper;
	
	
	@BeforeEach
	public void setup() {
		movies.add(new Movie(1l, "Don", "Aman Kumar", null, "It is a very good movie", null, null, null));
		movies.add(new Movie(2l, "Don 2", "Aman Kumar", null, "It is a very good movie", null, null, null));
		movies.add(new Movie(3l, "Don 3", "Aman Kumar", null, "It is a very good movie", null, null, null));
	}
	
	
	@Test
	@Order(1)
	public void test_getAllMovies() {
		
		when(restTemplate.getForObject("http://rating-service/ratings/movie/1", Object.class)).thenReturn(new RatingDto(9.0, 5l));
		when(restTemplate.getForObject("http://rating-service/ratings/movie/2", Object.class)).thenReturn(new RatingDto(9.0, 5l));
		when(restTemplate.getForObject("http://rating-service/ratings/movie/3", Object.class)).thenReturn(new RatingDto(9.0, 5l));
		
		when(mapper.map(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(new RatingDto(9.0, 5l));
		
		when(movieRepository.findAll()).thenReturn(movies);
		
		List<Movie> allMovies = movieService.getAllMovies();
		
		assertEquals(allMovies.get(0).getRating().getRating(), 9.0);
		
	}
	
	@Test
	@Order(2)
	public void test_getMovieById() {
		when(restTemplate.getForObject("http://rating-service/ratings/movie/1", Object.class)).thenReturn(new RatingDto(9.0, 5l));
		
		when(mapper.map(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(new RatingDto(9.0, 5l));
		
		when(movieRepository.findById(1l)).thenReturn(Optional.of(movies.get(0)));
		
		Movie movieById = movieService.getMovieById(1l);
		
		assertEquals(movieById.getRating().getRating(), 9.0);
		assertEquals(movieById.getRating().getNumberofUsersRated(),5l);
	}
	
	
	@Test
	@Order(3)
	public void test_getMovieByNameOrDirector() {
		
		when(movieRepository.findMovieByNameOrDirector(anyString())).thenReturn
		((List.of(new Movie(1L, "Don", "Akash", null, null, null, null, null)
				,new Movie(2L, "Don 2", "Akash", null, null, null, null, null))));
		
		when(restTemplate.getForObject("http://rating-service/ratings/movie/1", Object.class)).thenReturn(new RatingDto(9.0, 200L));
		when(restTemplate.getForObject("http://rating-service/ratings/movie/2", Object.class)).thenReturn(new RatingDto(9.0, 200L));
		
		when(mapper.map(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(new RatingDto(9.0, 200L));
		
		List<Movie> movieByMovieNameOrDirector = movieService.getMovieByMovieNameOrDirector("Don");
		
		assertEquals(movieByMovieNameOrDirector.size(), 2);
		assertEquals(movieByMovieNameOrDirector.get(0).getMovieName(), "Don");
		assertEquals(movieByMovieNameOrDirector.get(1).getRating().getRating(), 9.0);
	}
	
}
