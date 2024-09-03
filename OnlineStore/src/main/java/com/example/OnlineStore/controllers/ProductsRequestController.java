package com.example.OnlineStore.controllers;

import com.example.OnlineStore.payload.response.ProductResponse;
import com.example.OnlineStore.payload.response.ProductsResponse;
import com.example.OnlineStore.payload.request.ProductRequest;
import com.example.OnlineStore.services.ProductsRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/productRequest")
public class ProductsRequestController {
    @Autowired
    private ProductsRequestService productsRequestService;

    @RequestMapping(value ="/returnAllProducts")
    public ResponseEntity<ProductsResponse> returnAllProducts(){
        return ResponseEntity.ok(productsRequestService.getAll());
    }

    @RequestMapping(value ="/returnProductById")
    public ResponseEntity<ProductResponse> returnProductById(@RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productsRequestService.getProductById(productRequest.getId()));
    }

    @DeleteMapping(value ="/deleteProduct")
    public ResponseEntity<String> deleteProduct(@RequestBody ProductRequest productRequest) {
      return  ResponseEntity.ok(productsRequestService.deleteProduct(productRequest.getId()));
    }
    @PostMapping(value ="/addProduct")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productsRequestService.addProduct(productRequest));
    }

    @PatchMapping(value ="/updateProduct")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productsRequestService.updateProduct(productRequest));
    }

}
