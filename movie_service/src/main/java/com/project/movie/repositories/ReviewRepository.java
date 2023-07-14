package com.project.movie.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.movie.entities.Review;


public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query("select r from Review r where r.userId=:userId")
	public List<Review> findReviewByUserId(@Param("userId")Long userId);
}

