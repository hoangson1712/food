package com.example.demo.service;

import com.example.demo.request.OrderRequest;
import com.example.demo.response.OrderUserResponse;

import java.util.List;

public interface OrderService {
    boolean placeOrder(OrderRequest orderRequest,int userId);
    List<OrderUserResponse> userOrder(int userId);
    List<OrderUserResponse> listOrder();
    boolean updateOrderStatus(int orderId,String orderStatus);
}
