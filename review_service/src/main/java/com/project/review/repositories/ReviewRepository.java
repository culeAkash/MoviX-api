package com.project.review.repositories;

import com.project.review.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    public Review findByMovieIdAndUserId(Long movieId,Long userId);
}
