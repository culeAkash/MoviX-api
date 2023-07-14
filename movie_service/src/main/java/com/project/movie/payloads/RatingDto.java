package com.project.movie.payloads;

public class RatingDto {

	private Double rating;
	private Long numberofUsersRated;
	
	public RatingDto(Double rating, Long numberofUsersRated) {
		super();
		this.rating = rating;
		this.numberofUsersRated = numberofUsersRated;
	}

	public RatingDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Long getNumberofUsersRated() {
		return numberofUsersRated;
	}

	public void setNumberofUsersRated(Long numberofUsersRated) {
		this.numberofUsersRated = numberofUsersRated;
	}

	@Override
	public String toString() {
		return "RatingDto [rating=" + rating + ", numberofUsersRated=" + numberofUsersRated + "]";
	}
	
	
	
}
