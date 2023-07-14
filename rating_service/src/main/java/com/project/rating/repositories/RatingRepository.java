package com.project.rating.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.rating.entities.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

	@Query("select r from Rating r where r.userId=:userId and r.movieId=:movieId")
	public Rating findByMovieAndUser(@Param("userId") Long userId,@Param("movieId") Long movieId);

	@Query("select r from Rating r where r.movieId=:movieId")
	public List<Rating> findRatingsByMovie(@Param("movieId") Long movieId);
	
	

}
