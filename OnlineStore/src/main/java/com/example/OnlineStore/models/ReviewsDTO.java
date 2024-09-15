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
    Long id;
    Integer rating;
    String comment;
    String date;
    String reviewerName;
    String reviewerEmail;
}
