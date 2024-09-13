package com.example.OnlineStore.controllers;

import com.example.OnlineStore.exceptions.RuntimeErrorResponse;
import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.models.ProductDTO;
import com.example.OnlineStore.payload.request.ProductRequest;
import com.example.OnlineStore.payload.response.GenericResponses;
import com.example.OnlineStore.payload.response.ResponseEnum;
import com.example.OnlineStore.services.ProductsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductsService productsService;

    @GetMapping(value ="/getAll")
    public ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.ok(productsService.getAll());
    }

    @GetMapping(value = "/deleteProduct")
    public ResponseEntity<ResponseEnum> deleteProduct(@RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productsService.deleteProduct(productRequest));
    }
    @GetMapping(value = "/findProductById")
    public ResponseEntity<ProductDTO> findProductById(@RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productsService.findById(productRequest));
    }

    @PostMapping(value = "/addProduct")
    public ResponseEntity<GenericResponses> addProduct(@RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productsService.addProduct(productRequest));
    }
    @PatchMapping(value = "/updateProduct")
   public ResponseEntity<GenericResponses> updateProduct(@RequestBody ProductRequest productRequest){
       return ResponseEntity.ok(productsService.updateProduct(productRequest));
   }
}
