package com.example.OnlineStore.payload.request;

import com.example.OnlineStore.payload.response.MetaResponse;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    Long id;
    String title;
    String description;
    String category;
    Integer price;
    Float discountPercentage;
    Float rating;
    Integer stock;
    String brand;
    String sku;
    Integer weight;
    String warrantyInformation;
    String shippingInformation;
    String availabilityStatus;
    String returnPolicy;
    Integer minimumOrderQuantity;
    MetaResponse meta;
    String thumbnail;
    List<String>images;
}
