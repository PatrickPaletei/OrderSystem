package com.example.ordersystem.dto.response;

import lombok.Data;

@Data
public class BaseResponse<T> {
    private String status = "T";
    private String message = "";
    private T data;
}
