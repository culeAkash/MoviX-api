package com.project.user.payloads;

public class MovieDto {

	
		private Long movieId;



		private String movieName;


		private String director;



	private String imageUrl;

	private String synopsis;

	private double rating;

	private int usersReviewed;

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getUsersReviewed() {
		return usersReviewed;
	}

	public void setUsersReviewed(int usersReviewed) {
		this.usersReviewed = usersReviewed;
	}

	public MovieDto(Long movieId, String movieName, String director, String imageUrl, String synopsis, double rating,
			int usersReviewed) {
		super();
		this.movieId = movieId;
		this.movieName = movieName;
		this.director = director;
		this.imageUrl = imageUrl;
		this.synopsis = synopsis;
		this.rating = rating;
		this.usersReviewed = usersReviewed;
	}

	public MovieDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
