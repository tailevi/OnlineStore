package com.example.OnlineStore.services;

import com.example.OnlineStore.exceptions.ProductsServiceException;
import com.example.OnlineStore.exceptions.RuntimeErrorResponse;
import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.payload.request.ProductRequest;
import com.example.OnlineStore.payload.response.GenericResponses;
import com.example.OnlineStore.payload.response.ResponseEnum;
import com.example.OnlineStore.reposetories.ProductRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService {
    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAll() {
        return productRepo.findAll();
    }
    public String print() {
        return "hi";
    }
    @SneakyThrows
    public ResponseEnum deleteProduct(ProductRequest productRequest){
        try {
            // Assuming deleteById throws an unchecked exception if the item is not found
            productRepo.deleteById(productRequest.getId());
            return ResponseEnum.DELETED;
        } catch (Exception e) {
            // Handle the exception and throw a custom exception with a descriptive message
            throw new ProductsServiceException("Item with ID " + productRequest.getId() + " was not found or could not be deleted");
        }
    }

    @SneakyThrows
    public Product findById(@NotNull ProductRequest productRequest){
        return productRepo.findById(productRequest.getId())
                .orElseThrow(()
                        -> new ProductsServiceException("Item with ID " + productRequest.getId() + " was not found or could not be deleted"));
    }

    @SneakyThrows
    @Transactional
    public GenericResponses addProduct(@NotNull ProductRequest productRequest) {
        Product product = Product.builder()
                .title(productRequest.getTitle())
                .brand(productRequest.getBrand())
                .price(productRequest.getPrice())
                .sku(productRequest.getSku())
                .availabilityStatus(productRequest.getAvailabilityStatus())
                .category(productRequest.getCategory())
                .description(productRequest.getDescription())
                .stock(productRequest.getStock())
                .discountPercentage(productRequest.getDiscountPercentage())
                .images(productRequest.getImages())
                .weight(productRequest.getWeight())
                .thumbnail(productRequest.getThumbnail())
                .returnPolicy(productRequest.getReturnPolicy())
                .warrantyInformation(productRequest.getWarrantyInformation())
                .shippingInformation(productRequest.getShippingInformation())
                .build();

        Product prodResponse = productRepo.save(product);

        return GenericResponses.builder()
                .id(prodResponse.getId())
                .title(prodResponse.getTitle())
                .message(ResponseEnum.ADDED.toString())
                .build();
    }

    @Transactional
    public GenericResponses updateProduct(@NotNull ProductRequest productRequest){
        Optional<Product> product = productRepo.findById(productRequest.getId());

            if (product.isPresent()) {
                Product existingProduct = product.get();
                existingProduct.setTitle(productRequest.getTitle());
                existingProduct.setDescription(productRequest.getDescription());
                existingProduct.setCategory(productRequest.getCategory());
                existingProduct.setPrice(productRequest.getPrice());
                existingProduct.setDiscountPercentage(productRequest.getDiscountPercentage());
                existingProduct.setRating(productRequest.getRating());
                existingProduct.setStock(productRequest.getStock());
                existingProduct.setBrand(productRequest.getBrand());
                existingProduct.setSku(productRequest.getSku());
                existingProduct.setWeight(productRequest.getWeight());
                existingProduct.setWarrantyInformation(productRequest.getWarrantyInformation());
                existingProduct.setShippingInformation(productRequest.getShippingInformation());
                existingProduct.setAvailabilityStatus(productRequest.getAvailabilityStatus());
                existingProduct.setReturnPolicy(productRequest.getReturnPolicy());
                existingProduct.setThumbnail(productRequest.getThumbnail());
                existingProduct.setImages(productRequest.getImages());
                productRepo.save(existingProduct);

                return GenericResponses.builder()
                        .id(existingProduct.getId())
                        .title(existingProduct.getTitle())
                        .message(ResponseEnum.UPDATED.toString())
                        .build();
            }
            throw new ProductsServiceException("Item with ID " + productRequest.getId() + " was not found or could not be deleted");
        }
    }

