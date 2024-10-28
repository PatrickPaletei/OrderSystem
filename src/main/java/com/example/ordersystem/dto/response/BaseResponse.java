package com.example.ordersystem.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BaseResponse<T> {
    private String status = "T";
    private String message = "";
    private T data;
}
