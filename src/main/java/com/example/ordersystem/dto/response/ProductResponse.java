package com.example.ordersystem.dto.response;

import com.example.ordersystem.entity.MstProduct;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    private List<MstProduct> ListProducts;
    private int pageNum;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
