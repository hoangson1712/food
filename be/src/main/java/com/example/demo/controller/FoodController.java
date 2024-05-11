package com.example.demo.controller;

import com.example.demo.request.FoodRequest;
import com.example.demo.request.RemoveFoodRequest;
import com.example.demo.response.FoodResponse;
import com.example.demo.service.imp.FoodServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    private FoodServiceImp foodServiceImp;

    @PostMapping("/add")
    public ResponseEntity<?> addFood(@RequestBody FoodRequest foodRequest){
        FoodResponse foodResponse = new FoodResponse();
        if(foodServiceImp.addFood(foodRequest)){
            foodResponse.setSuccess(true);
            foodResponse.setMessage("Food Added");
        }else{
            foodResponse.setSuccess(false);
            foodResponse.setMessage("Error");
        }
        return new ResponseEntity<>(foodResponse, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listFood(){
        FoodResponse foodResponse = new FoodResponse();
        try {
            foodResponse.setSuccess(true);
            foodResponse.setData(foodServiceImp.listFood());
            return new ResponseEntity<>(foodResponse, HttpStatus.OK);
        }catch (Exception e){
            foodResponse.setSuccess(false);
            foodResponse.setMessage("Error");
            return new ResponseEntity<>(foodResponse, HttpStatus.OK);
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeFood(@RequestBody RemoveFoodRequest request){
        boolean isSuccess = foodServiceImp.removeFood(request.getFoodId());
        FoodResponse foodResponse = new FoodResponse();
        if(isSuccess){
            foodResponse.setSuccess(true);
            foodResponse.setMessage("Food Removed");
        }else{
            foodResponse.setSuccess(false);
            foodResponse.setMessage("Error");
        }
        return new ResponseEntity<>(foodResponse, HttpStatus.OK);
    }
}
