package com.project.rating;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.project.rating.entities.Rating;
import com.project.rating.payloads.MovieRatingResponse;
import com.project.rating.repositories.RatingRepository;
import com.project.rating.services.impl.RatingServiceImpl;

@SpringBootTest(classes = {ServiceMockitoTests.class})
public class ServiceMockitoTests {

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private RatingRepository ratingRepository;

	@InjectMocks
	private RatingServiceImpl ratingService;

	List<Rating> ratings = new ArrayList<>();

	@BeforeEach
	public void setup() {
		ratings.add(new Rating(1L, 8.0, 1L, 1L));
		ratings.add(new Rating(2L, 7.0, 2L, 1L));
		ratings.add(new Rating(3L, 10.0, 3L, 1L));
	}

	@Test
	@Order(1)
	public void test_getAverageRatingOfMovie() {

		when(restTemplate.getForObject("http://movie-service/services/movies/isPresent/1", Boolean.class)).thenReturn(true);

		when(ratingRepository.findRatingsByMovie(1L)).thenReturn(ratings);

		MovieRatingResponse averageRatingOfMovie = ratingService.getAverageRatingOfMovie(1L);

		assertEquals(averageRatingOfMovie.getRating(), 8.3);
	}

	@Test
	@Order(2)
	public void test_getRatingOfMovieByUser() throws Exception {

		when(restTemplate.getForObject("http://user-service/services/users/isLoggedIn/1", Boolean.class)).thenReturn(true);

		when(restTemplate.getForObject("http://user-service/services/users/confirmUserPresence/1", Boolean.class)).thenReturn(true);

		when(restTemplate.getForObject("http://movie-service/services/movies/isPresent/1", Boolean.class)).thenReturn(true);

		when(ratingRepository.findByMovieAndUser(1L, 1L)).thenReturn(ratings.get(0));

		Double ratingOfMovieByUser = ratingService.getRatingOfMovieByUser(1L, 1L);

		assertEquals(ratingOfMovieByUser, 8.0);
	}
}
