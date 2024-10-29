package com.example.ordersystem.dto.response;

import com.example.ordersystem.entity.MstOrderCart;
import lombok.Data;

import java.util.List;

@Data
public class OrderChartResponse {
    private List<MstOrderCart> listOrderCarts;
    private int pageNum;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
