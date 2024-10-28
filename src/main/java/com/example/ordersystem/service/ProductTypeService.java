package com.example.ordersystem.service;

import com.example.ordersystem.dto.request.UpdateProductTypeRequest;
import com.example.ordersystem.dto.response.BaseResponse;
import com.example.ordersystem.entity.MstProductType;
import org.hibernate.mapping.Any;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductTypeService {
    ResponseEntity<BaseResponse<Any>> addProductType (String productTypeName);
    ResponseEntity<BaseResponse<List<MstProductType>>> getAllProductType();
    ResponseEntity<BaseResponse<Any>> updateProductType (MstProductType existProductType, UpdateProductTypeRequest updateProductTypeRequest);
    ResponseEntity<BaseResponse<Any>> deleteProductType (MstProductType productType);
}
