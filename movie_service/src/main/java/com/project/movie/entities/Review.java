package com.project.movie.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.movie.payloads.UserDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Review {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;

	@NotBlank(message = "Review must not be blank")
	private String reviewContent;

	@ManyToOne
	@JoinColumn(name = "movie_id")
	@JsonIgnoreProperties("reviews")
	private Movie movie;
	
	private Long userId;
	
	@Transient
	private UserDto user;

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public Review(Long reviewId, @NotBlank(message = "Review must not be blank") String reviewContent, Movie movie,
			Long userId, UserDto user) {
		super();
		this.reviewId = reviewId;
		this.reviewContent = reviewContent;
		this.movie = movie;
		this.userId = userId;
		this.user = user;
	}

	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", reviewContent=" + reviewContent + ", movie=" + movie + ", userId="
				+ userId + ", user=" + user + "]";
	}
	
	
	
	
	
	

}
