package com.project.review.payloads;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class RatingDTO {
    private Long ratingId;

    @NotNull(message = "Rating must not be null")
    @Min(value = 1, message = "Minimum rating that can be given is 1")
    @Max(value = 10, message = "Maximum rating that can be given is 10")
    private Integer ratingValue;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @NotNull(message = "Movie ID cannot be null")
    private Long movieId;

    @NotNull(message = "User ID cannot be null")
    private Long userId;
}
