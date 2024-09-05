package com.project.movie.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private Long movieId;
    @NotBlank(message = "Movie Name must not be empty!")
    private String movieName;


    @NotBlank(message = "Movie Director Name must not be empty!")
    @Size(min = 4, max = 20, message = "Minimum 4 and Maximum 20 characters are allowed!!!")
    private String director;


    private String videoUrl;
    private String imageUrl;

    @Size(max = 1000,message = "Synopsis can't be of length more than 1000")
    private String synopsis;


    private RatingDto ratingDto;
}
