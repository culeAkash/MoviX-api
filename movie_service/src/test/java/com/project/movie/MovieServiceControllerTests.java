package com.project.movie;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.project.movie.controllers.MovieController;
import com.project.movie.entities.Movie;
import com.project.movie.exceptions.ForbiddenRequestException;
import com.project.movie.services.FileService;
import com.project.movie.services.MovieService;

@WebMvcTest({MovieController.class})
public class MovieServiceControllerTests{
	
	@MockBean
	MovieService movieService;
	
	@MockBean
	FileService fileService;
	
	@Autowired
	MockMvc mockMvc;
	
	
	@Test
	@Order(1)
	public void test_createNewMovie() throws Exception {
		
		Movie movie = new Movie();
		movie.setMovieName("Don");
		movie.setDirector("Aman Kumar");
		
		when(movieService.createNewMovie(movie)).thenReturn(new Movie(1l,movie.getMovieName(),movie.getDirector(),null,null,null,null,null));
		
		mockMvc.perform(post("/movies",movie).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(movie)))
		.andExpect(status().isAccepted());
	}
	
	@Test
	@Order(2)
	public void test_deleteMovie() throws Exception {
		when(movieService.deleteMovie(anyLong())).thenReturn(new Movie());
		
		mockMvc.perform(delete("/movies/{movieId}",1L).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
	
	@Test
	@Order(3)
	public void test_getAllMovies() throws Exception {
		List<Movie> movies = new ArrayList<>();
		
		movies.add(new Movie(1L, "Don", "Aman Kumar", null, null, null, null, null));
		movies.add(new Movie(2L, "Don 2", "Aman Kumar", null, null, null, null, null));
		movies.add(new Movie(3L, "Don 3", "Aman Kumar", null, null, null, null, null));
		movies.add(new Movie(4L, "Don 4", "Aman Kumar", null, null, null, null, null));
		
		
		when(movieService.getAllMovies()).thenReturn(movies);
		
		mockMvc.perform(get("/movies").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].director").value("Aman Kumar"));
	}
	
	
	@Test
	@Order(4)
	public void test_getMovieById() throws Exception{
		when(movieService.getMovieById(1L)).thenReturn(new Movie(1L, "Don", "Akash", null, null, null, null, null));
		
		mockMvc.perform(get("/movies/id/{movieId}",1L).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.movieName").value("Don"));
	}
	
	
	@Test
	@Order(5)
	public void test_getMovieBySearchVal() throws Exception{
		when(movieService.getMovieByMovieNameOrDirector("Don")).thenReturn
		((List.of(new Movie(1L, "Don", "Akash", null, null, null, null, null)
		,new Movie(2L, "Don 2", "Akash", null, null, null, null, null))));
		
		mockMvc.perform(get("/movies/search/{nameDir}","Don").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray());
		
	}
	
}
