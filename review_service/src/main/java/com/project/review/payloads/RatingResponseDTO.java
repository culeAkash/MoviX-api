package com.project.review.payloads;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingResponseDTO {
    private Double ratingValue;
    private Integer userRatedCount;
}
