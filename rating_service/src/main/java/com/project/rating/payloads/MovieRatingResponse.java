package com.project.rating.payloads;

public class MovieRatingResponse {

	private Double rating;
	private Long numberOfUsersRated;
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public Long getNumberOfUsersRated() {
		return numberOfUsersRated;
	}
	public void setNumberOfUsersRated(Long numberOfUsersRated) {
		this.numberOfUsersRated = numberOfUsersRated;
	}
	public MovieRatingResponse(Double rating, Long numberOfUsersRated) {
		super();
		this.rating = rating;
		this.numberOfUsersRated = numberOfUsersRated;
	}
	public MovieRatingResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "MovieRatingResponse [rating=" + rating + ", numberOfUsersRated=" + numberOfUsersRated + "]";
	}
	
	
}
