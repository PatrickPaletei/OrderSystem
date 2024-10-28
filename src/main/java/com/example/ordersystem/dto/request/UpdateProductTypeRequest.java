package com.example.ordersystem.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UpdateProductTypeRequest {
    private Integer productTypeId;
    private String productType;
}
