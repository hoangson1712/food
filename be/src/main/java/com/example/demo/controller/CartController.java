package com.example.demo.controller;

import com.example.demo.request.FoodIdRequest;
import com.example.demo.response.CartResponse;
import com.example.demo.response.UserIdResponse;
import com.example.demo.response.UserResponse;
import com.example.demo.service.imp.CartServiceImp;
import com.example.demo.service.imp.JwtServiceImp;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private JwtServiceImp jwtServiceImp;

    @Autowired
    private CartServiceImp cartServiceImp;

    private Gson gson = new Gson();

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody FoodIdRequest foodIdRequest, @RequestHeader String token){
        CartResponse cartResponse = new CartResponse();
        if(token == null || token.trim().isEmpty()){
            cartResponse.setSuccess(false);
            cartResponse.setMessage("Not Authorized Login Again");
        }else{
            UserIdResponse userIdResponse = gson.fromJson(jwtServiceImp.decryptToken(token),UserIdResponse.class);
            if(cartServiceImp.addToCart(foodIdRequest.getFoodId(), userIdResponse.getUserId())){
                cartResponse.setSuccess(true);
                cartResponse.setMessage("Added To Cart");
            }else{
                cartResponse.setSuccess(false);
                cartResponse.setMessage("Error");
            }
        }
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeFromCart(@RequestBody FoodIdRequest foodIdRequest, @RequestHeader String token){
        CartResponse cartResponse = new CartResponse();
        if(token == null || token.trim().isEmpty()){
            cartResponse.setSuccess(false);
            cartResponse.setMessage("Not Authorized Login Again");
        }else{
            UserIdResponse userIdResponse = gson.fromJson(jwtServiceImp.decryptToken(token),UserIdResponse.class);
            if(cartServiceImp.removeFromCart(foodIdRequest.getFoodId(), userIdResponse.getUserId())){
                cartResponse.setSuccess(true);
                cartResponse.setMessage("Removed From Cart");
            }else{
                cartResponse.setSuccess(false);
                cartResponse.setMessage("Error");
            }
        }
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getCart(@RequestHeader String token){
        CartResponse cartResponse = new CartResponse();
        if(token == null || token.trim().isEmpty()){
            cartResponse.setSuccess(false);
            cartResponse.setMessage("Not Authorized Login Again");
        }else{
            UserIdResponse userIdResponse = gson.fromJson(jwtServiceImp.decryptToken(token),UserIdResponse.class);
            if(cartServiceImp.getCart(userIdResponse.getUserId()) == null || cartServiceImp.getCart(userIdResponse.getUserId()).isEmpty()){
                cartResponse.setSuccess(false);
                cartResponse.setMessage("Error");
            }else{
                cartResponse.setSuccess(true);
                cartResponse.setCartData(cartServiceImp.getCart(userIdResponse.getUserId()));
            }
        }
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCart(@RequestHeader String token){
        CartResponse cartResponse = new CartResponse();
        if(token == null || token.trim().isEmpty()){
            cartResponse.setSuccess(false);
            cartResponse.setMessage("Not Authorized Login Again");
        }else{
            UserIdResponse userIdResponse = gson.fromJson(jwtServiceImp.decryptToken(token),UserIdResponse.class);
            if(cartServiceImp.deleteCart(userIdResponse.getUserId())){
                cartResponse.setSuccess(true);
            }else{
                cartResponse.setSuccess(false);
                cartResponse.setMessage("Delete Cart Fail");
            }
        }
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }
}
