package com.example.ordersystem.controller;

import com.example.ordersystem.dto.request.AddProductRequest;
import com.example.ordersystem.dto.response.BaseResponse;
import com.example.ordersystem.dto.response.ProductResponse;
import com.example.ordersystem.entity.MstProduct;
import com.example.ordersystem.entity.MstProductType;
import com.example.ordersystem.repository.ProductRepository;
import com.example.ordersystem.repository.ProductTypeRepository;
import com.example.ordersystem.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.mapping.Any;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/products")
@Tag(name = "Product API")
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;

    public ProductController(ProductService productService, ProductRepository productRepository, ProductTypeRepository productTypeRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.productTypeRepository = productTypeRepository;
    }

    @PostMapping
    ResponseEntity<BaseResponse<Any>> addProduct(@RequestBody AddProductRequest addProductRequest) {
        BaseResponse<Any> response = new BaseResponse<>();
        if (addProductRequest.getProductName() == null || addProductRequest.getPrice() == 0.0) {
            response.setStatus("F");
            response.setMessage("Product name and product price is required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        MstProduct existProduct = productRepository.findByProductNameIgnoreCase(addProductRequest.getProductName());
        if (existProduct != null) {
            response.setStatus("F");
            response.setMessage("Product name already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Optional<MstProductType> existProductTypeId = productTypeRepository.findById(addProductRequest.getIdProductType());
        if (existProductTypeId.isEmpty()) {
            response.setStatus("F");
            response.setMessage("Product type not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        MstProductType productType = existProductTypeId.get();
        return productService.addProduct(addProductRequest, productType);
    }

    @GetMapping
    ResponseEntity<BaseResponse<ProductResponse>> getProducts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return productService.getAllProduct(pageNo, pageSize);
    }

    @GetMapping("{product-id}")
    ResponseEntity<BaseResponse<MstProduct>> getProductById(@PathVariable("product-id") Integer productId) {
        BaseResponse<MstProduct> response = new BaseResponse<>();
        if (productId == null) {
            response.setStatus("F");
            response.setMessage("Product id is required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Optional<MstProduct> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            response.setStatus("F");
            response.setMessage("Product id " + productId + " not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return productService.getProduct(productId);
    }

    @PutMapping
    ResponseEntity<BaseResponse<Any>> updateProduct(@RequestBody AddProductRequest updateProduct) {
        BaseResponse<Any> response = new BaseResponse<>();
        if (updateProduct.getProductId() == null) {
            response.setStatus("F");
            response.setMessage("Product id is required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Optional<MstProduct> productOptional = productRepository.findById(updateProduct.getProductId());
        if (productOptional.isEmpty()) {
            response.setStatus("F");
            response.setMessage("Product id " + updateProduct.getProductId() + " not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        MstProduct existProduct = productOptional.get();
        Optional<MstProductType> productType = productTypeRepository.findById(updateProduct.getIdProductType());
        if (productType.isEmpty()) {
            response.setStatus("F");
            response.setMessage("Product type " + updateProduct.getIdProductType() + " not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        MstProductType existProductType = productType.get();
        return productService.updateProduct(existProduct, updateProduct, existProductType);

    }

    @DeleteMapping("{id-product}")
    ResponseEntity<BaseResponse<Any>> deleteProduct(@PathVariable("id-product") Integer productId) {
        BaseResponse<Any> response = new BaseResponse<>();
        if (productId == null) {
            response.setStatus("F");
            response.setMessage("Product id is required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Optional<MstProduct> existProduct = productRepository.findById(productId);
        if (existProduct.isEmpty()) {
            response.setStatus("F");
            response.setMessage("Product id " + productId + " not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return productService.deleteProduct(existProduct.get().getIdProduct());
    }

}
