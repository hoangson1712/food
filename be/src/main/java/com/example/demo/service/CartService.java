package com.example.demo.service;

import com.example.demo.response.UserIdResponse;

import java.util.Map;

public interface CartService {
    boolean addToCart(int foodId, int userId);
    boolean removeFromCart(int foodId, int userId);
    Map<String, Integer> getCart(int userId);
    boolean deleteCart(int userId);
}
