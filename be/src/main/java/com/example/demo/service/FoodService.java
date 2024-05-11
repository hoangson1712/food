package com.example.demo.service;

import com.example.demo.request.FoodRequest;
import com.example.demo.response.ListFoodResponse;

import java.util.List;

public interface FoodService {
    boolean addFood(FoodRequest foodRequest);
    List<ListFoodResponse> listFood();
    boolean removeFood(int foodId);
}
