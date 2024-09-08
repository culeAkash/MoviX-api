package com.project.review.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long reviewId;

    @NotEmpty(message = "Review must not be empty")
    private String reviewContent;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @NotNull(message = "Movie ID cannot be null")
    private Long movieId;

    @NotNull(message = "User ID cannot be null")
    private Long userId;
}
