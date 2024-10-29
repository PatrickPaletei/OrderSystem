package com.example.ordersystem.dto.request;

import lombok.Data;


@Data
public class AddProductRequest {
    private Integer productId;
    private String productName;
    private Integer idProductType;
    private double price;
}
