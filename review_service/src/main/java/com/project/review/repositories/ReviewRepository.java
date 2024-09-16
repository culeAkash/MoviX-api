package com.project.review.repositories;

import com.project.review.entities.Rating;
import com.project.review.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    public List<Review> findByMovieId(Long movieId);

    public Optional<Review> findByMovieIdAndUserId(Long movieId, Long userId);

    public void deleteAllByMovieId(Long movieId);
}
