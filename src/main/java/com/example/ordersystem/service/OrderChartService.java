package com.example.ordersystem.service;

import com.example.ordersystem.dto.request.AddOrderChartRequest;
import com.example.ordersystem.dto.request.UpdateOrderRequest;
import com.example.ordersystem.dto.request.UpdateOrderStatusRequest;
import com.example.ordersystem.dto.response.BaseResponse;
import com.example.ordersystem.dto.response.OrderChartResponse;
import com.example.ordersystem.entity.MstOrderCart;
import com.example.ordersystem.entity.MstProduct;
import com.example.ordersystem.entity.MstProductType;
import com.example.ordersystem.entity.MstUser;
import org.hibernate.mapping.Any;
import org.springframework.http.ResponseEntity;

public interface OrderChartService {
    ResponseEntity<BaseResponse<Any>> addOrder(AddOrderChartRequest newOrder, MstProduct product, MstUser user);

    ResponseEntity<BaseResponse<OrderChartResponse>> getAllOrder(int pageNo, int pageSize);

    ResponseEntity<BaseResponse<OrderChartResponse>> getOrderByUserId(int userId, int pageNo, int pageSize);

    ResponseEntity<BaseResponse<Any>> updateOrderQuantity(MstOrderCart existOrder,UpdateOrderRequest updateOrder);

    ResponseEntity<BaseResponse<Any>> updateStatusOrder(MstOrderCart existOrder, UpdateOrderStatusRequest updateOrder);

    ResponseEntity<BaseResponse<Any>> deleteOrder(int orderId);
}
