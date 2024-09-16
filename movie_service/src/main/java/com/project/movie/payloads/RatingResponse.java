package com.project.movie.payloads;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingResponse {
    private Double ratingValue;
    private Integer userRatedCount;
}
