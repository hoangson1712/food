package com.example.demo.request;

import java.util.List;

public class OrderRequest {
    private OrderAddress orderAddress;
    private List<OrderItems> orderItems;
    private float orderAmount;

    public OrderAddress getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(OrderAddress orderAddress) {
        this.orderAddress = orderAddress;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public float getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(float orderAmount) {
        this.orderAmount = orderAmount;
    }
}
