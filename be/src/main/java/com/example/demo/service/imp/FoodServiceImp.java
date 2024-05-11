package com.example.demo.service.imp;

import com.example.demo.entity.FoodEntity;
import com.example.demo.repository.FoodRepository;
import com.example.demo.request.FoodRequest;
import com.example.demo.response.ListFoodResponse;
import com.example.demo.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImp implements FoodService {
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public boolean addFood(FoodRequest foodRequest) {
        boolean isSuccess = false;
        try {
            FoodEntity foodEntity = new FoodEntity();
            foodEntity.setFoodName(foodRequest.getFoodName());
            foodEntity.setFoodDesc(foodRequest.getFoodDesc());
            foodEntity.setFoodPrice(foodRequest.getFoodPrice());
            foodEntity.setFoodImage(foodRequest.getFoodImage());
            foodEntity.setFoodCategory(foodRequest.getFoodCategory());
            foodRepository.save(foodEntity);
            isSuccess = true;
        }catch (Exception e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return isSuccess;
    }

    @Override
    public List<ListFoodResponse> listFood() {
        List<FoodEntity> list = foodRepository.findAll();
        List<ListFoodResponse> list1 = new ArrayList<>();
        for (FoodEntity item : list) {
            ListFoodResponse listFoodResponse = new ListFoodResponse();
            listFoodResponse.setFoodId(item.getFoodId());
            listFoodResponse.setFoodName(item.getFoodName());
            listFoodResponse.setFoodDesc(item.getFoodDesc());
            listFoodResponse.setFoodPrice(item.getFoodPrice());
            listFoodResponse.setFoodImage(item.getFoodImage());
            listFoodResponse.setFoodCategory(item.getFoodCategory());
            list1.add(listFoodResponse);
        }
        return list1;
    }

    @Override
    public boolean removeFood(int foodId) {
        boolean isSuccess = false;
        try{
            if (foodRepository.existsById(foodId)){
                foodRepository.deleteById(foodId);
                isSuccess = true;
            }
        }catch (Exception e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return isSuccess;
    }
}
