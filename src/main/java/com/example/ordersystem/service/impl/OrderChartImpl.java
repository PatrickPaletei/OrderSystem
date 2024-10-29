package com.example.ordersystem.service.impl;

import com.example.ordersystem.dto.request.AddOrderChartRequest;
import com.example.ordersystem.dto.request.UpdateOrderRequest;
import com.example.ordersystem.dto.request.UpdateOrderStatusRequest;
import com.example.ordersystem.dto.response.BaseResponse;
import com.example.ordersystem.dto.response.OrderChartResponse;
import com.example.ordersystem.entity.MstOrderCart;
import com.example.ordersystem.entity.MstProduct;
import com.example.ordersystem.entity.MstUser;
import com.example.ordersystem.repository.OrderChartRepository;
import com.example.ordersystem.repository.UserRepository;
import com.example.ordersystem.service.OrderChartService;
import org.hibernate.mapping.Any;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderChartImpl implements OrderChartService {
    private final OrderChartRepository orderChartRepository;
    private final UserRepository userRepository;

    public OrderChartImpl(OrderChartRepository orderChartRepository, UserRepository userRepository) {
        this.orderChartRepository = orderChartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<BaseResponse<Any>> addOrder(AddOrderChartRequest newOrder, MstProduct product, MstUser user) {
        MstOrderCart newOrderChart = new MstOrderCart();
        newOrderChart.setIdProduct(product);
        newOrderChart.setIdUser(user);
        newOrderChart.setDate(newOrder.getOrderDate());
        newOrderChart.setQuantity(newOrder.getQuantity());
        newOrderChart.setStatus("false");
        orderChartRepository.save(newOrderChart);
        BaseResponse<Any> response = new BaseResponse<>();
        response.setMessage("Order " + newOrderChart.getIdProduct().getProductName() + " added successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BaseResponse<OrderChartResponse>> getAllOrder(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<MstOrderCart> orders = orderChartRepository.findAll(pageable);
        List<MstOrderCart> orderList = orders.getContent();

        BaseResponse<OrderChartResponse> response = new BaseResponse<>();
        OrderChartResponse orderChartResponse = new OrderChartResponse();
        orderChartResponse.setListOrderCarts(orderList);
        orderChartResponse.setPageNum(orders.getNumber());
        orderChartResponse.setPageSize(orders.getSize());
        orderChartResponse.setTotalElements(orders.getTotalElements());
        orderChartResponse.setTotalPages(orders.getTotalPages());
        orderChartResponse.setLast(orders.isLast());

        response.setMessage("ALl Order Cart Successfully retrived");
        response.setData(orderChartResponse);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BaseResponse<OrderChartResponse>> getOrderByUserId(int userId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        MstUser user = userRepository.findById(userId).orElse(null);
        Page<MstOrderCart> orders = orderChartRepository.findByIdUser(user, pageable);
        List<MstOrderCart> orderList = orders.getContent();

        BaseResponse<OrderChartResponse> response = new BaseResponse<>();
        OrderChartResponse orderChartResponse = new OrderChartResponse();
        orderChartResponse.setListOrderCarts(orderList);
        orderChartResponse.setPageNum(orders.getNumber());
        orderChartResponse.setPageSize(orders.getSize());
        orderChartResponse.setTotalElements(orders.getTotalElements());
        orderChartResponse.setTotalPages(orders.getTotalPages());
        orderChartResponse.setLast(orders.isLast());

        response.setMessage("Order Cart for userId " + userId + " Successfully retrived");
        response.setData(orderChartResponse);
        return ResponseEntity.ok(response);

    }

    @Override
    public ResponseEntity<BaseResponse<Any>> updateOrderQuantity(
            MstOrderCart existOrder, UpdateOrderRequest updateOrder) {
        Integer currentQuantity = existOrder.getQuantity();
        if (!currentQuantity.equals(updateOrder.getQuantity())) {
            existOrder.setQuantity(updateOrder.getQuantity());
        }
        MstOrderCart updatedOrder = orderChartRepository.save(existOrder);
        BaseResponse<Any> response = new BaseResponse<>();
        response.setMessage("Quantity Order " + updatedOrder.getIdProduct().getProductName() + " updated successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BaseResponse<Any>> updateStatusOrder(MstOrderCart existOrder, UpdateOrderStatusRequest updateOrder) {
        String currentStatus = existOrder.getStatus();
        if (!currentStatus.equals(updateOrder.getStatus())) {
            existOrder.setStatus(updateOrder.getStatus());
        }
        MstOrderCart updatedStatusOrder = orderChartRepository.save(existOrder);
        BaseResponse<Any> response = new BaseResponse<>();
        response.setMessage("Status Order " + updatedStatusOrder.getIdProduct().getProductName() + " updated successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BaseResponse<Any>> deleteOrder(int orderId) {
        orderChartRepository.deleteById(orderId);
        BaseResponse<Any> response = new BaseResponse<>();
        response.setMessage("Order " + orderId + " deleted");
        return ResponseEntity.ok(response);
    }
}
