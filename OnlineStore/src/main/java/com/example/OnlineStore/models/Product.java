package com.example.OnlineStore.models;

import com.example.OnlineStore.payload.response.DimensionsResponse;
import com.example.OnlineStore.payload.response.MetaResponse;
import com.example.OnlineStore.payload.response.ReviewsResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;
import java.util.List;
@Entity
@Table(name ="product")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    List<Reviews> reviews;
    String returnPolicy;
    String thumbnail;
    List<String>images;
}
