package com.example.ordersystem.service;

import com.example.ordersystem.dto.request.AddProductRequest;
import com.example.ordersystem.dto.response.BaseResponse;
import com.example.ordersystem.dto.response.ProductResponse;
import com.example.ordersystem.entity.MstProduct;
import com.example.ordersystem.entity.MstProductType;
import org.hibernate.mapping.Any;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<BaseResponse<Any>> addProduct(AddProductRequest newProduct, MstProductType productType);

    ResponseEntity<BaseResponse<ProductResponse>> getAllProduct(int pageNo, int pageSize);

    ResponseEntity<BaseResponse<MstProduct>> getProduct(Integer idProduct);

    ResponseEntity<BaseResponse<Any>> updateProduct(MstProduct existProduct, AddProductRequest updatedProduct, MstProductType productType);

    ResponseEntity<BaseResponse<Any>> deleteProduct(Integer idProduct);
}
