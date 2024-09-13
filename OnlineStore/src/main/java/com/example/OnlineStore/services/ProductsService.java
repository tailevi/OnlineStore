package com.example.OnlineStore.services;
import com.example.OnlineStore.models.ProductDTO;
import com.example.OnlineStore.models.Reviews;
import com.example.OnlineStore.models.ReviewsDTO;
import org.hibernate.Hibernate;
import org.springframework.data.redis.core.RedisTemplate;
import com.example.OnlineStore.exceptions.ProductsServiceException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductsService {
    @Autowired
    private ProductRepo productRepo;


    @Autowired
    private RedisTemplate<String, ProductDTO> redisTemplate;

    @Autowired
    private RedisTemplate<String, List<ReviewsDTO>> reviewRedisTemplate;

    private static final String PRODUCT_CACHE_PREFIX = "product_";
    private static final String PRODUCT_CACHE_REVIEW_PREFIX = "reviews_";

    public List<Product> getAll() {
        return productRepo.findAll();
    }

    @SneakyThrows
    public ResponseEnum deleteProduct(ProductRequest productRequest){
        try {
            productRepo.deleteById(productRequest.getId());
            String  cacheKey = PRODUCT_CACHE_PREFIX+ productRequest.getId();
            redisTemplate.delete(cacheKey);
            return ResponseEnum.DELETED;
        } catch (Exception e) {
            // Handle the exception and throw a custom exception with a descriptive message
            throw new ProductsServiceException("Item with ID " + productRequest.getId() + " was not found or could not be deleted");
        }
    }

    @SneakyThrows
    @Transactional
    public ProductDTO findById(@NotNull ProductRequest productRequest){
        Long id =  productRequest.getId();

        String cacheKey = PRODUCT_CACHE_PREFIX +id;
        ProductDTO cachedProduct = redisTemplate.opsForValue().get(cacheKey);


        if (cachedProduct != null) {
            List<ReviewsDTO> reviewsList = reviewRedisTemplate.opsForValue().get(PRODUCT_CACHE_REVIEW_PREFIX + id);
            cachedProduct.setReviewsDTOList(reviewsList);
            return cachedProduct;
        }

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ProductsServiceException("Product not found with ID: " + id));
        ProductDTO productDTO = mapToDTO(product);
        redisTemplate.opsForValue().set(cacheKey, productDTO);
        List <ReviewsDTO> reviewsList = getCachedReviews(productDTO.getId(), product);
        productDTO.setReviewsDTOList(reviewsList);
        return productDTO;
    }

    private ProductDTO mapToDTO(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .category(product.getCategory())
                .title(product.getTitle())
                .build();
    }

    @Transactional
    private List<ReviewsDTO> getCachedReviews(Long id, Product cachedProduct) {
        String cacheKey = PRODUCT_CACHE_REVIEW_PREFIX + id;

        List<ReviewsDTO> reviewsDTOList = reviewRedisTemplate.opsForValue().get(cacheKey);

        if (reviewsDTOList != null) {
            return reviewsDTOList;
        }
        List<Reviews> reviews = (cachedProduct.getReviews() != null && !cachedProduct.getReviews().isEmpty())
                ? cachedProduct.getReviews()
                : new ArrayList<>();

        reviewsDTOList = reviews.stream()
                .map(this::mapToReviewDTO)
                .collect(Collectors.toList());
        reviewRedisTemplate.opsForValue().set(cacheKey, reviewsDTOList);

        return reviewsDTOList;
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

    private ReviewsDTO mapToReviewDTO(Reviews review) {
        ReviewsDTO reviewDTO = new ReviewsDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setComment(review.getComment());
        reviewDTO.setRating(review.getRating());
        return reviewDTO;
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

                String cacheKey = PRODUCT_CACHE_PREFIX + productRequest.getId();
                ProductDTO productDTO = mapToDTO(existingProduct);
                redisTemplate.opsForValue().set(cacheKey, productDTO);

                return GenericResponses.builder()
                        .id(existingProduct.getId())
                        .title(existingProduct.getTitle())
                        .message(ResponseEnum.UPDATED.toString())
                        .build();
            }
            throw new ProductsServiceException("Item with ID " + productRequest.getId() + " was not found or could not be deleted");
        }
    }

