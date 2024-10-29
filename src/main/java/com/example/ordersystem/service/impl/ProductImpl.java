package com.example.ordersystem.service.impl;

import com.example.ordersystem.dto.request.AddProductRequest;
import com.example.ordersystem.dto.response.BaseResponse;
import com.example.ordersystem.dto.response.ProductResponse;
import com.example.ordersystem.entity.MstProduct;
import com.example.ordersystem.entity.MstProductType;
import com.example.ordersystem.repository.ProductRepository;
import com.example.ordersystem.service.ProductService;
import org.hibernate.mapping.Any;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<BaseResponse<Any>> addProduct(AddProductRequest newProduct, MstProductType productType) {
        MstProduct mstProduct = new MstProduct();
        mstProduct.setProductName(newProduct.getProductName());
        mstProduct.setProductType(productType);
        mstProduct.setPrice(newProduct.getPrice());
        productRepository.save(mstProduct);
        BaseResponse<Any> response = new BaseResponse<>();
        response.setMessage("Product " + newProduct.getProductName() + " added successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponse>> getAllProduct(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<MstProduct> products = productRepository.findAll(pageable);
        List<MstProduct> productList = products.getContent();

        BaseResponse<ProductResponse> response = new BaseResponse<>();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setListProducts(productList);
        productResponse.setPageNum(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setLast(products.isLast());

        response.setMessage("Product " + products.getTotalElements() + " Successfully retrived");
        response.setData(productResponse);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BaseResponse<MstProduct>> getProduct(Integer idProduct) {
        BaseResponse<MstProduct> response = new BaseResponse<>();
        Optional<MstProduct> product = productRepository.findById(idProduct);
        if (product.isEmpty()) {
            response.setMessage("Product with id " + idProduct + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        MstProduct mstProduct = product.get();
        response.setMessage("Product " + mstProduct + " found successfully");
        response.setData(mstProduct);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BaseResponse<Any>> updateProduct(
            MstProduct existProduct,
            AddProductRequest updatedProduct,
            MstProductType productType) {
        String currentProductName = existProduct.getProductName();
        Integer currentProductType = existProduct.getProductType().getIdProductType();
        double currentPrice = existProduct.getPrice();
        if (!currentProductName.equals(updatedProduct.getProductName())) {
            existProduct.setProductName(updatedProduct.getProductName());
        }
        if (!currentProductType.equals(updatedProduct.getIdProductType())) {
            //questioable
            existProduct.setProductType(productType);
        }
        if (currentPrice != updatedProduct.getPrice()) {
            existProduct.setPrice(updatedProduct.getPrice());
        }
        MstProduct updatedMstProduct = productRepository.save(existProduct);
        BaseResponse<Any> response = new BaseResponse<>();
        response.setMessage("Product " + updatedMstProduct.getProductName() + " updated successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BaseResponse<Any>> deleteProduct(Integer idProduct) {
        productRepository.deleteById(idProduct);
        BaseResponse<Any> response = new BaseResponse<>();
        response.setMessage("Product " + idProduct + " deleted successfully");
        return ResponseEntity.ok(response);
    }
}
