package com.example.OnlineStore.services;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.models.Reviews;
import com.example.OnlineStore.payload.response.DimensionsResponse;
import com.example.OnlineStore.payload.response.ProductResponse;
import com.example.OnlineStore.payload.response.ProductsResponse;
import com.example.OnlineStore.payload.request.ProductRequest;
import com.example.OnlineStore.payload.response.ReviewsResponse;
import com.example.OnlineStore.reposetories.ProductRepo;
import com.example.OnlineStore.reposetories.ReviewesRepo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductsRequestService {

    public static final String url = "https://dummyjson.com/products";
    @Autowired
    private RestTemplate URLRequest;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ReviewesRepo reviewesRepo;
    public ProductsResponse getAll(){
        ProductsResponse prod=  URLRequest.getForObject(url, ProductsResponse.class);
        assert prod != null;
        List<Product> productList = prod.getProducts().stream()
                .map(this::mapToProductEntity)
                .collect(Collectors.toList());
        productRepo.saveAll(productList);
        return prod;
    }
    private Product mapToProductEntity(ProductResponse response) {
        Product product = Product.builder()
                .id(response.getId())
                .title(response.getTitle())
                .description(response.getDescription())
                .category(response.getCategory())
                .price(response.getPrice())
                .discountPercentage(response.getDiscountPercentage())
                .rating(response.getRating())
                .stock(response.getStock())
                .tags(response.getTags())
                .brand(response.getBrand())
                .sku(response.getSku())
                .weight(response.getWeight())
                .warrantyInformation(response.getWarrantyInformation())
                .shippingInformation(response.getShippingInformation())
                .availabilityStatus(response.getAvailabilityStatus())
                .returnPolicy(response.getReturnPolicy())
                .thumbnail(response.getThumbnail())
                .images(response.getImages()).build();

        List<Reviews> reviewsList = response.getReviews().stream()
                .map(reviewResponse -> mapToReviewEntity(reviewResponse, product))
                .collect(Collectors.toList());
        product.setReviews(reviewsList);
        return product;
    }
    private Reviews mapToReviewEntity(ReviewsResponse reviewResponse, Product product) {
        return Reviews.builder()
                .rating(reviewResponse.getRating())
                .comment(reviewResponse.getComment())
                .date(reviewResponse.getDate())
                .reviewerName(reviewResponse.getReviewerName())
                .reviewerEmail(reviewResponse.getReviewerEmail())
                //.product(product)
                .build();
    }
    @SneakyThrows
    public ProductResponse getProductById(@NotNull Long id){
        String getById = url+"/"+id;
        return URLRequest.getForObject(getById, ProductResponse.class);
    }
    @SneakyThrows
    public String deleteProduct(long productId) {
        String url = "url" + productId;
        URLRequest.delete(url);
       return "Product with ID " + productId + " has been deleted.";
    }
    @SneakyThrows
    public ProductResponse addProduct(ProductRequest product){
        String getById = url+"/add";
        return URLRequest.postForObject(getById, product,ProductResponse.class);
    }

    @SneakyThrows
    public ProductResponse updateProduct(@NotNull ProductRequest productRequest){
        String getById = url+"/"+productRequest.getId();
        ProductResponse product = new ProductResponse();
        product.setId(productRequest.getId());
        product.setTitle(productRequest.getTitle());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setDiscountPercentage(productRequest.getDiscountPercentage());
        product.setRating(productRequest.getRating());
        product.setStock(productRequest.getStock());
        product.setBrand(productRequest.getBrand());
        product.setSku(productRequest.getSku());
        product.setWeight(productRequest.getWeight());
        product.setWarrantyInformation(productRequest.getWarrantyInformation());
        product.setShippingInformation(productRequest.getShippingInformation());
        product.setAvailabilityStatus(productRequest.getAvailabilityStatus());
        product.setReturnPolicy(productRequest.getReturnPolicy());
        product.setMinimumOrderQuantity(productRequest.getMinimumOrderQuantity());
        product.setThumbnail(productRequest.getThumbnail());
        product.setImages(productRequest.getImages());
        return URLRequest.postForObject(getById, product,ProductResponse.class);
    }

}
