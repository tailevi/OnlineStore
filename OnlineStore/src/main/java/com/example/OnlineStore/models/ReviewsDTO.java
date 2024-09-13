package com.example.OnlineStore.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewsDTO {
    private Long id;
    private String comment;
    private int rating;
}
