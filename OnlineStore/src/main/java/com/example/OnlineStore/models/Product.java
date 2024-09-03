package com.example.OnlineStore.models;

import com.example.OnlineStore.payload.response.DimensionsResponse;
import com.example.OnlineStore.payload.response.MetaResponse;
import com.example.OnlineStore.payload.response.ReviewsResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;
@Entity
@Table(name ="product")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    List<Reviews> reviews;
    String returnPolicy;
    String thumbnail;
    List<String>images;
}
