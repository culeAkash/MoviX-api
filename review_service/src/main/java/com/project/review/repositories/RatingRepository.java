package com.project.review.repositories;

import com.project.review.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    // find all ratings by movieId
    public List<Rating> findByMovieId(Long movieId);

    public Rating findByMovieIdAndUserId(Long movieId,Long userId);
}
