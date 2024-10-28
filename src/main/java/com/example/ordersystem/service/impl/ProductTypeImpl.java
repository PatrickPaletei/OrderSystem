package com.example.ordersystem.service.impl;

import com.example.ordersystem.dto.request.UpdateProductTypeRequest;
import com.example.ordersystem.dto.response.BaseResponse;
import com.example.ordersystem.entity.MstProductType;
import com.example.ordersystem.repository.ProductTypeRepository;
import com.example.ordersystem.service.ProductTypeService;
import org.hibernate.mapping.Any;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeImpl implements ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    public ProductTypeImpl(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    @Override
    public ResponseEntity<BaseResponse<Any>> addProductType(String productTypeName) {
        MstProductType mstProductType = new MstProductType();
        mstProductType.setProductType(productTypeName);
        productTypeRepository.save(mstProductType);
        BaseResponse<Any> response = new BaseResponse<>();
        response.setMessage("Status " + productTypeName + " added successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BaseResponse<List<MstProductType>>> getAllProductType() {
        List<MstProductType> productTypeList = productTypeRepository.findAll();
        BaseResponse<List<MstProductType>> response = new BaseResponse<>();
        if (productTypeList.isEmpty()) {
            response.setMessage("No productType found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.setMessage("Successfully retrieved " + productTypeList.size() + " productType");
        response.setData(productTypeList);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BaseResponse<Any>> updateProductType(
            MstProductType existProductType,
            UpdateProductTypeRequest updateProductTypeRequest) {
        String currentProductTypeName = existProductType.getProductType();
        String newProductTypeName = updateProductTypeRequest.getProductType();
        if (!currentProductTypeName.equals(newProductTypeName)) {
            existProductType.setProductType(newProductTypeName);
        }
        MstProductType updatedProductType = productTypeRepository.save(existProductType);
        BaseResponse<Any> response = new BaseResponse<>();
        response.setMessage("Status " + updatedProductType.getProductType() + " updated successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BaseResponse<Any>> deleteProductType(MstProductType productType) {
        productTypeRepository.delete(productType);
        BaseResponse<Any> response = new BaseResponse<>();
        response.setMessage("Status " + productType.getProductType() + " deleted successfully");
        return ResponseEntity.ok(response);
    }


}
