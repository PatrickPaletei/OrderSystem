package com.example.ordersystem.dto.response;

import com.example.ordersystem.entity.MstOrderCart;
import com.example.ordersystem.entity.MstUser;
import lombok.Data;

import java.util.List;

@Data
public class OrderChartDataResponse {
    private MstUser userData;
    private List<MstOrderCart> orderCarts;
}
