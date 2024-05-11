package com.example.demo.service.imp;

import com.example.demo.entity.OrderEntity;
import com.example.demo.repository.OrderRepository;
import com.example.demo.request.OrderAddress;
import com.example.demo.request.OrderItems;
import com.example.demo.request.OrderRequest;
import com.example.demo.response.OrderUserResponse;
import com.example.demo.service.OrderService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    private Gson gson = new Gson();

    @Override
    public boolean placeOrder(OrderRequest orderRequest, int userId) {
        boolean isSuccess = false;
        try {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOrderUserId(userId);
            orderEntity.setOrderItems(gson.toJson(orderRequest.getOrderItems()));
            orderEntity.setOrderAmount(orderRequest.getOrderAmount());
            orderEntity.setOrderAddress(gson.toJson(orderRequest.getOrderAddress()));
            orderRepository.save(orderEntity);
            isSuccess = true;
        }catch (Exception e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return isSuccess;
    }

    @Override
    public List<OrderUserResponse> userOrder(int userId) {
        List<OrderEntity> list = orderRepository.findByOrderUserId(userId);
        List<OrderUserResponse> list1 = new ArrayList<>();
        Type orderItemsListType = new TypeToken<List<OrderItems>>(){}.getType();
        for (OrderEntity item : list) {
            OrderUserResponse orderUserResponse = new OrderUserResponse();
            orderUserResponse.setOrderId(item.getOrderId());
            orderUserResponse.setOrderUserId(item.getOrderUserId());
            orderUserResponse.setOrderItems(gson.fromJson(item.getOrderItems(), orderItemsListType));
            orderUserResponse.setOrderAmount(item.getOrderAmount());
            orderUserResponse.setOrderAddress(gson.fromJson(item.getOrderAddress(), OrderAddress.class));
            orderUserResponse.setOrderStatus(item.getOrderStatus());
            orderUserResponse.setOrderDate(item.getOrderDate());
            list1.add(orderUserResponse);
        }
        return list1;
    }

    @Override
    public List<OrderUserResponse> listOrder() {
        List<OrderEntity> list = orderRepository.findAll();
        List<OrderUserResponse> list1 = new ArrayList<>();
        Type orderItemsListType = new TypeToken<List<OrderItems>>(){}.getType();
        for (OrderEntity item : list) {
            OrderUserResponse orderUserResponse = new OrderUserResponse();
            orderUserResponse.setOrderId(item.getOrderId());
            orderUserResponse.setOrderUserId(item.getOrderUserId());
            orderUserResponse.setOrderItems(gson.fromJson(item.getOrderItems(), orderItemsListType));
            orderUserResponse.setOrderAmount(item.getOrderAmount());
            orderUserResponse.setOrderAddress(gson.fromJson(item.getOrderAddress(), OrderAddress.class));
            orderUserResponse.setOrderStatus(item.getOrderStatus());
            orderUserResponse.setOrderDate(item.getOrderDate());
            list1.add(orderUserResponse);
        }
        return list1;
    }

    @Override
    public boolean updateOrderStatus(int orderId,String orderStatus) {
        boolean isSuccess = false;
        try {
            OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
            orderEntity.setOrderStatus(orderStatus);
            orderRepository.save(orderEntity);
            isSuccess = true;
        }catch (Exception e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return isSuccess;
    }
}
