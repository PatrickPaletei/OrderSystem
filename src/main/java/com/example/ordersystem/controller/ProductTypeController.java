package com.example.ordersystem.controller;

import com.example.ordersystem.dto.request.UpdateProductTypeRequest;
import com.example.ordersystem.dto.response.BaseResponse;
import com.example.ordersystem.entity.MstProductType;
import com.example.ordersystem.repository.ProductTypeRepository;
import com.example.ordersystem.service.ProductTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.mapping.Any;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/product-type")
@Tag(name = "Product Type API")
public class ProductTypeController {
    private final ProductTypeService productTypeService;
    private final ProductTypeRepository productTypeRepository;

    public ProductTypeController(ProductTypeService productTypeService, ProductTypeRepository productTypeRepository) {
        this.productTypeService = productTypeService;
        this.productTypeRepository = productTypeRepository;
    }

    @PostMapping("{product-name}")
    ResponseEntity<BaseResponse<Any>> addProductType(@PathVariable("product-name") String productname) {
        if (productname.isEmpty()) {
            BaseResponse<Any> response = new BaseResponse<>();
            response.setStatus("F");
            response.setMessage("Product name must not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        MstProductType existProductType = productTypeRepository.findByProductTypeIgnoreCase(productname);
        if (existProductType != null) {
            BaseResponse<Any> response = new BaseResponse<>();
            response.setStatus("F");
            response.setMessage("Product name already exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return productTypeService.addProductType(productname);
    }

    @GetMapping("all")
    ResponseEntity<BaseResponse<List<MstProductType>>> getAllProductTypes() {
        return productTypeService.getAllProductType();
    }

    @PutMapping
    ResponseEntity<BaseResponse<Any>> updateProductType(@RequestBody UpdateProductTypeRequest productType) {
        if (productType.getProductTypeId() == null) {
            BaseResponse<Any> response = new BaseResponse<>();
            response.setStatus("F");
            response.setMessage("Product type id must not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Optional<MstProductType> optionalProductType = productTypeRepository.findById(productType.getProductTypeId());
        if (optionalProductType.isEmpty()) {
            BaseResponse<Any> response = new BaseResponse<>();
            response.setStatus("F");
            response.setMessage("Product type not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        MstProductType existProductType = optionalProductType.get();
        if (productType.getProductType() != null) {
            if (existProductType.getProductType().equals(productType.getProductType())){
                BaseResponse<Any> response = new BaseResponse<>();
                response.setStatus("F");
                response.setMessage("Product type is the same!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }
        return productTypeService.updateProductType(existProductType, productType);
    }

    @DeleteMapping("{id-product-type}")
    ResponseEntity<BaseResponse<Any>> deleteProductType(@PathVariable("id-product-type") Integer idproducttype) {
        Optional<MstProductType> existProductType = productTypeRepository.findById(idproducttype);
        if (existProductType.isEmpty()) {
            BaseResponse<Any> response = new BaseResponse<>();
            response.setStatus("F");
            response.setMessage("Product type not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return productTypeService.deleteProductType(existProductType.get());
    }

}