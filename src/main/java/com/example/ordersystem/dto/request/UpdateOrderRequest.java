package com.example.ordersystem.dto.request;

import lombok.Data;

@Data
public class UpdateOrderRequest {
    private Integer idOrderChart;
    private Integer quantity;
}
