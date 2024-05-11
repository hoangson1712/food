package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "order_user_id")
    private int orderUserId;

    @Column(name = "order_items")
    private String orderItems;

    @Column(name = "order_amount")
    private float orderAmount;

    @Column(name = "order_address")
    private String orderAddress;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @PrePersist
    protected void onCreate() {
        orderDate = LocalDate.now();
        orderStatus = "Food Processing";
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(int orderUserId) {
        this.orderUserId = orderUserId;
    }

    public String getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(String orderItems) {
        this.orderItems = orderItems;
    }

    public float getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(float orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}
