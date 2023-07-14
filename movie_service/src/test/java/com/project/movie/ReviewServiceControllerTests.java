package com.project.movie;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.movie.controllers.ReviewController;
import com.project.movie.entities.Review;
import com.project.movie.services.ReviewService;

@WebMvcTest({ReviewController.class})
public class ReviewServiceControllerTests {

	@MockBean
	ReviewService reviewService;
	
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	@Order(1)
	public void test_createNewReview() throws Exception{
		Review review = new Review(null, "It is a very good movie", null, null, null);
		
		when(reviewService.createReview(any(Review.class), anyLong(), anyLong())).thenReturn(new Review(1L, review.getReviewContent(), null, null, null));
		
		mockMvc.perform(post("/movies/{movieId}/user/{userId}/reviews",anyLong(),anyLong(),any(Review.class)).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(review)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.reviewId").value(1L));
	}
	
	@Test
	@Order(2)
	public void test_updateReview() throws Exception{
		Review review = new Review(1L, "It is a very good movie", null, null, null);
		
		when(reviewService.updateReview(anyLong(), any(Review.class))).thenReturn(review);
		
		mockMvc.perform(put("/movies/reviews/{reviewId}",anyLong(),any(Review.class))
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(review)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.reviewContent").value("It is a very good movie"));
	}
	
	@Test
	@Order(3)
	public void test_getAllReviewsOfMovie() throws Exception {
		List<Review> reviews= new ArrayList<>();
		
		reviews.add(new Review(1L, "This is a good movie", null, null, null));
		reviews.add(new Review(2L, "It is a very bad movie", null, null, null));
		
		when(reviewService.getAllReviewsOfMovie(anyLong())).thenReturn(reviews);
		
		mockMvc.perform(get("/movies/{movieId}/reviews",anyLong()).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray());
		
	}
}
