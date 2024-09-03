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
public class ProductsResponse implements Serializable {
    List<ProductResponse> products;
    Integer total;
    Integer skip;
    Integer limit;
}
