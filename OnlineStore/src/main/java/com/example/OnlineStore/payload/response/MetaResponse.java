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
public class MetaResponse implements Serializable {
    String createdAt;
    String updatedAt;
    String barcode;
    String qrCode;
}
