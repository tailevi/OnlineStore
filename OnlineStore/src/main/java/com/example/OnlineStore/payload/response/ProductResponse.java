package com.example.OnlineStore.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse implements Serializable {
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
    DimensionsResponse dimensions;
    String warrantyInformation;
    String shippingInformation;
    String availabilityStatus;
    List<ReviewsResponse> reviews;
    String returnPolicy;
    Integer minimumOrderQuantity;
    MetaResponse meta;
    String thumbnail;
    List<String>images;
}
