package com.project.user.payloads;

public class ReviewDto {

	
	private Long reviewId;

	
	private String reviewContent;

	private MovieDto movie;

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

	public MovieDto getMovie() {
		return movie;
	}

	public void setMovie(MovieDto movie) {
		this.movie = movie;
	}

	public ReviewDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReviewDto(Long reviewId, String reviewContent, MovieDto movie) {
		super();
		this.reviewId = reviewId;
		this.reviewContent = reviewContent;
		this.movie = movie;
	}
	
	
	
}
