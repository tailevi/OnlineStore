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
    Integer price;
    Float discountPercentage;
    Float rating;
    Integer stock;
    List<String> tags;
    String brand;
    String sku;
    Integer weight;
    String warrantyInformation;
    String shippingInformation;
    String availabilityStatus;
    List<ReviewsDTO> reviewsDTOList;
    String returnPolicy;
    String thumbnail;
    List<String>images;
}
