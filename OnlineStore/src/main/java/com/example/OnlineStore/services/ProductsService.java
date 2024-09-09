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
            Optional<Product> productOptional = productRepo.findById(productRequest.getId());

            if (productOptional.isPresent()) {
                Product existingProduct = productOptional.get();

                Optional.ofNullable(productRequest.getTitle()).ifPresent(existingProduct::setTitle);
                Optional.ofNullable(productRequest.getDescription()).ifPresent(existingProduct::setDescription);
                Optional.ofNullable(productRequest.getCategory()).ifPresent(existingProduct::setCategory);
                Optional.ofNullable(productRequest.getPrice()).ifPresent(existingProduct::setPrice);
                Optional.ofNullable(productRequest.getDiscountPercentage()).ifPresent(existingProduct::setDiscountPercentage);
                Optional.ofNullable(productRequest.getRating()).ifPresent(existingProduct::setRating);
                Optional.ofNullable(productRequest.getStock()).ifPresent(existingProduct::setStock);
                Optional.ofNullable(productRequest.getBrand()).ifPresent(existingProduct::setBrand);
                Optional.ofNullable(productRequest.getSku()).ifPresent(existingProduct::setSku);
                Optional.ofNullable(productRequest.getWeight()).ifPresent(existingProduct::setWeight);
                Optional.ofNullable(productRequest.getWarrantyInformation()).ifPresent(existingProduct::setWarrantyInformation);
                Optional.ofNullable(productRequest.getShippingInformation()).ifPresent(existingProduct::setShippingInformation);
                Optional.ofNullable(productRequest.getAvailabilityStatus()).ifPresent(existingProduct::setAvailabilityStatus);
                Optional.ofNullable(productRequest.getReturnPolicy()).ifPresent(existingProduct::setReturnPolicy);
                Optional.ofNullable(productRequest.getThumbnail()).ifPresent(existingProduct::setThumbnail);
                Optional.ofNullable(productRequest.getImages()).ifPresent(existingProduct::setImages);

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

