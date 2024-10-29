package com.example.ordersystem.dto.request;

import lombok.Data;

@Data
public class UpdateOrderStatusRequest {
    private Integer idOrderChart;
    private String status;
}
