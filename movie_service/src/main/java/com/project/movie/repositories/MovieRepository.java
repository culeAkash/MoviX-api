package com.project.movie.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.movie.entities.Movie;



public interface MovieRepository extends JpaRepository<Movie, Long> {


	@Query("select m from Movie m where m.movieName like %:input% or m.director like %:input%")
	public List<Movie> findMovieByNameOrDirector(@Param("input")String input);

}