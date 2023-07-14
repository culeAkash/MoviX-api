package com.project.movie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.project.movie.entities.Movie;
import com.project.movie.entities.Review;
import com.project.movie.payloads.UserDto;
import com.project.movie.repositories.MovieRepository;
import com.project.movie.repositories.ReviewRepository;
import com.project.movie.services.impl.ReviewServiceImpl;

@SpringBootTest(classes = {ReviewServiceTests.class})
public class ReviewServiceTests {
	
	@Mock
	MovieRepository movieRepository;
	
	@Mock
	ReviewRepository reviewRepository;
	
	@Mock 
	RestTemplate restTemplate;
	
	@Mock
	ModelMapper mappper;
	
	@InjectMocks
	ReviewServiceImpl reviewService;
	
	List<Review> reviews = new ArrayList<>();
	Movie movie;
	
	@BeforeEach
	public void setup() {
		reviews.add(new Review(1l, "It is good movie", null, 1l, null));
		reviews.add(new Review(2l, "It is bad movie", null, 2l, null));
		movie = new Movie(1l, "Don", "Aman Kumar", null, "It stars Shahrukh Khan", reviews, null, null);
	}
	
	
	@Test
	@Order(1)
	public void test_getAllReviewsOfMovie() {
		when(movieRepository.findById(1l)).thenReturn(Optional.of(movie));
		
		when(restTemplate.getForObject("http://user-service/services/users/getUser/1", Object.class)).thenReturn(new UserDto("Akash", "akash@gmail.com", "Akash@123", 0));
		when(restTemplate.getForObject("http://user-service/services/users/getUser/2", Object.class)).thenReturn(null);
		
		when(mappper.map(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(new UserDto("Akash", "akash@gmail.com", "Akash@123", 0) );
		
		
		List<Review> allReviewsOfMovie = reviewService.getAllReviewsOfMovie(1l);
		
		assertEquals(allReviewsOfMovie.get(0).getUser().getEmail(), "akash@gmail.com");
		assertThat(allReviewsOfMovie.get(1).getUser()).isNull();
	}
	
	
	@Test
	@Order(2)
	public void test_getAllReviewsByUserId() {
		reviews.get(1).setUserId(1l);
		
		when(reviewRepository.findReviewByUserId(1l)).thenReturn(reviews);
		
		when(restTemplate.getForObject("http://user-service/services/users/getUser/1", Object.class)).thenReturn(new UserDto("Akash", "akash@gmail.com", "Akash@123", 0) );
		
		when(mappper.map(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(new UserDto("Akash", "akash@gmail.com", "Akash@123", 0) );
		
		List<Review> allReviewsByUserId = reviewService.getAllReviewsByUserId(1l);
		
		assertEquals(allReviewsByUserId.size(), 2);
		assertEquals(allReviewsByUserId.get(0).getUser().getEmail(), "akash@gmail.com");
	}
	
	

}
