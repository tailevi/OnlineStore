package com.example.OnlineStore.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DimensionsResponse implements Serializable {
    Float width;
    Float height;
    Float depth;



}
