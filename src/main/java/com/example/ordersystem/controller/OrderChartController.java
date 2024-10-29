package com.example.ordersystem.controller;

import com.example.ordersystem.dto.request.AddOrderChartRequest;
import com.example.ordersystem.dto.request.UpdateOrderRequest;
import com.example.ordersystem.dto.request.UpdateOrderStatusRequest;
import com.example.ordersystem.dto.response.BaseResponse;
import com.example.ordersystem.dto.response.OrderChartResponse;
import com.example.ordersystem.entity.MstOrderCart;
import com.example.ordersystem.entity.MstProduct;
import com.example.ordersystem.entity.MstUser;
import com.example.ordersystem.repository.OrderChartRepository;
import com.example.ordersystem.repository.ProductRepository;
import com.example.ordersystem.repository.UserRepository;
import com.example.ordersystem.service.OrderChartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.mapping.Any;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/orders")
@Tag(name = "Order Chart API")
public class OrderChartController {
    private final OrderChartService orderChartService;
    private final OrderChartRepository orderChartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderChartController(OrderChartService orderChartService, OrderChartRepository orderChartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderChartService = orderChartService;
        this.orderChartRepository = orderChartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    ResponseEntity<BaseResponse<Any>> addOrder(@Valid @RequestBody AddOrderChartRequest addOrderChartRequest, BindingResult bindingResult) {
        BaseResponse<Any> response = new BaseResponse<>();
        if (bindingResult.hasErrors()) {
            response.setMessage("Invalid request: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.badRequest().body(response);
        }
        Optional<MstProduct> existProduct = productRepository.findById(addOrderChartRequest.getIdProduct());
        if (existProduct.isEmpty()) {
            response.setStatus("F");
            response.setMessage("Product not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        MstProduct product = existProduct.get();
        Optional<MstUser> mstUser = userRepository.findById(addOrderChartRequest.getIdUser());
        if (mstUser.isEmpty()) {
            response.setStatus("F");
            response.setMessage("User not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        MstUser user = mstUser.get();
        return orderChartService.addOrder(addOrderChartRequest, product, user);
    }

    @GetMapping
    ResponseEntity<BaseResponse<OrderChartResponse>> getOrders(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return orderChartService.getAllOrder(pageNo, pageSize);
    }

    @GetMapping("{user-id}")
    ResponseEntity<BaseResponse<OrderChartResponse>> getOrderByUserId(
            @PathVariable("user-id") int userId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return orderChartService.getOrderByUserId(userId, pageNo, pageSize);
    }

    @PutMapping("quantity")
    ResponseEntity<BaseResponse<Any>> updateOrderQuantity(@RequestBody UpdateOrderRequest updateOrderRequest) {
        BaseResponse<Any> response = new BaseResponse<>();
        if (updateOrderRequest.getIdOrderChart() == null) {
            response.setStatus("F");
            response.setMessage("Id Order is required!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Optional<MstOrderCart> existOrderOptional = orderChartRepository.findById(updateOrderRequest.getIdOrderChart());
        if (existOrderOptional.isEmpty()) {
            response.setStatus("F");
            response.setMessage("Order Chart not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        MstOrderCart existOrderChart = existOrderOptional.get();
        return orderChartService.updateOrderQuantity(existOrderChart, updateOrderRequest);
    }

    @PutMapping("status")
    ResponseEntity<BaseResponse<Any>> updateStatusOrder(@RequestBody UpdateOrderStatusRequest updateOrderRequest) {
        BaseResponse<Any> response = new BaseResponse<>();
        if (updateOrderRequest.getIdOrderChart() == null) {
            response.setStatus("F");
            response.setMessage("Id Order is required!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Optional<MstOrderCart> existOrderOptional = orderChartRepository.findById(updateOrderRequest.getIdOrderChart());
        if (existOrderOptional.isEmpty()) {
            response.setStatus("F");
            response.setMessage("Order Chart not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        MstOrderCart existOrderChart = existOrderOptional.get();
        return orderChartService.updateStatusOrder(existOrderChart, updateOrderRequest);
    }

    @DeleteMapping("{id-order-chart}")
    ResponseEntity<BaseResponse<Any>> deleteOrderChart(@PathVariable("id-order-chart") Integer idOrderChart) {
        BaseResponse<Any> response = new BaseResponse<>();
        if (idOrderChart == null) {
            response.setStatus("F");
            response.setMessage("Id Order is required!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Optional<MstOrderCart> existOrderOptional = orderChartRepository.findById(idOrderChart);
        if (existOrderOptional.isEmpty()) {
            response.setStatus("F");
            response.setMessage("Order Chart not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return orderChartService.deleteOrder(existOrderOptional.get().getIdOrderCart());
    }

}
