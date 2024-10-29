package com.example.ordersystem.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.util.Date;

@Data
public class AddOrderChartRequest {

    @NotNull(message = "Product ID cannot be null")
    private Integer idProduct;

    @NotNull(message = "User ID cannot be null")
    private Integer idUser;

    @NotNull(message = "Order Date cannot be null")
    @PastOrPresent(message = "Order Date cannot be in the future")
    private Date orderDate;

    @NotNull(message = "Quantity cannot be null")
    private Integer quantity;
}
