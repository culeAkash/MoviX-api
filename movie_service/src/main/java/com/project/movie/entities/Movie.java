package com.project.movie.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.movie.payloads.RatingDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long movieId;



	@NotBlank(message = "Movie Name must not be empty!")
	private String movieName;


	@NotBlank(message = "Movie Director Name must not be empty!")
	@Size(min = 4, max = 20, message = "Minimum 4 and Maximum 20 characters are allowed!!!")
	private String director;

	private String videoUrl;

	private String imageUrl;

	@Column(length = 1000)
	private String synopsis;


	@OneToMany(cascade = CascadeType.ALL,mappedBy = "movie")
	@JsonIgnoreProperties("movie")
	private List<Review> reviews = new ArrayList<>();
	
	
	@Transient
	private RatingDto rating;


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


	public List<Review> getReviews() {
		return reviews;
	}


	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}


	public RatingDto getRating() {
		return rating;
	}


	public void setRating(RatingDto rating) {
		this.rating = rating;
	}


	public Movie() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Movie(Long movieId, @NotBlank(message = "Movie Name must not be empty!") String movieName,
			@NotBlank(message = "Movie Director Name must not be empty!") @Size(min = 4, max = 20, message = "Minimum 4 and Maximum 20 characters are allowed!!!") String director,
			String imageUrl, String synopsis, List<Review> reviews, RatingDto rating,String videoUrl) {
		super();
		this.movieId = movieId;
		this.movieName = movieName;
		this.director = director;
		this.imageUrl = imageUrl;
		this.synopsis = synopsis;
		this.reviews = reviews;
		this.rating = rating;
		this.videoUrl = videoUrl;
	}


	@Override
	public String toString() {
		return "Movie [movieId=" + movieId + ", movieName=" + movieName + ", director=" + director + ", imageUrl="
				+ imageUrl + ", synopsis=" + synopsis + ", reviews=" + reviews + ", rating=" + rating + "]";
	}


	public String getVideoUrl() {
		return videoUrl;
	}


	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	

}




