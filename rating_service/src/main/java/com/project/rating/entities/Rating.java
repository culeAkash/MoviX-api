package com.project.rating.entities;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ratingId;

	@Range(min = 0,max=10,message = "rating must be between 0 and 10")
	private double rating;

	private Long userId;

	private Long movieId;

	public Long getRatingId() {
		return ratingId;
	}

	public void setRatingId(Long ratingId) {
		this.ratingId = ratingId;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public Rating(Long ratingId, double rating, Long userId, Long movieId) {
		this.ratingId = ratingId;
		this.rating = rating;
		this.userId = userId;
		this.movieId = movieId;
	}

	public Rating() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Rating [ratingId=" + ratingId + ", rating=" + rating + ", userId=" + userId + ", movieId=" + movieId
				+ "]";
	}


}
