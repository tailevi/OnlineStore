package com.example.OnlineStore.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    Long id;
    String title;
    String description;
    String category;
    List<ReviewsDTO> reviewsDTOList;
}
