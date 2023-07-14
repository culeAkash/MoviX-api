package com.project.rating;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.rating.controllers.RatingController;
import com.project.rating.entities.Rating;
import com.project.rating.payloads.MovieRatingResponse;
import com.project.rating.services.impl.RatingServiceImpl;

@WebMvcTest(RatingController.class)
public class ControllerMockitoTests {

	@MockBean
	RatingServiceImpl ratingService;

	@Autowired
	MockMvc mockMvc;

	@Test
	@Order(1)
	public void test_getAverageratingByMovie() throws Exception {
		when(ratingService.getAverageRatingOfMovie(1L)).thenReturn(new MovieRatingResponse(9.0, 10L));

		mockMvc.perform(get("/ratings/movie/{movieId}",1L).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted())
		.andExpect(jsonPath("$.rating").value(9.0));
	}


	@Test
	@Order(3)
	public void test_getRatingOfUserOnMovie() throws Exception{
		when(ratingService.getRatingOfMovieByUser(1L, 1L)).thenReturn(9.0);

		mockMvc.perform(get("/ratings/user/{userId}/movie/{movieId}",1L,1L).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(jsonPath("$").value(9.0));
	}

	@Test
	@Order(2)
	public void test_addOrUpdateNewRating() throws Exception{
		Rating rating = new Rating();
		rating.setRating(9.0);

		doNothing().when(ratingService).createNewRating(1L, 1L, rating);

		mockMvc.perform(post("/ratings/user/{userId}/movie/{movieId}",1L,1L,rating)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(rating)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.message").value("New Rating is created"))
		.andExpect(jsonPath("$.isSuccessful").value(true));

	}
}
