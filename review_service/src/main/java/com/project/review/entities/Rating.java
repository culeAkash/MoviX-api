package com.project.review.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "ratings",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"movieId", "userId"})
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @Min(value = 1,message = "Minimum Rating that can be given is 1")
    @Max(value = 10,message = "Maximum rating that can be given is 10")
    @ColumnDefault(value = "1")
    private Integer ratingValue;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long movieId;
    private Long userId;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
