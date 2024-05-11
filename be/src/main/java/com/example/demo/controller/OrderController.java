package com.example.demo.controller;

import com.example.demo.request.OrderRequest;
import com.example.demo.request.OrderStatusRequest;
import com.example.demo.response.OrderResponse;
import com.example.demo.response.UserIdResponse;
import com.example.demo.service.imp.JwtServiceImp;
import com.example.demo.service.imp.OrderServiceImp;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private JwtServiceImp jwtServiceImp;

    @Autowired
    private OrderServiceImp orderServiceImp;

    private Gson gson = new Gson();

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest, @RequestHeader String token){
        OrderResponse orderResponse = new OrderResponse();
        if(token == null || token.trim().isEmpty()){
            orderResponse.setSuccess(false);
            orderResponse.setMessage("Not Authorized Login Again");
        }else{
            UserIdResponse userIdResponse = gson.fromJson(jwtServiceImp.decryptToken(token),UserIdResponse.class);
            if (orderServiceImp.placeOrder(orderRequest, userIdResponse.getUserId())){
                orderResponse.setSuccess(true);
                orderResponse.setMessage("Placed Order");
            }else{
                orderResponse.setSuccess(false);
                orderResponse.setMessage("Error");
            }
        }
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @GetMapping("/userorder")
    public ResponseEntity<?> userOrder(@RequestHeader String token){
        OrderResponse orderResponse = new OrderResponse();
        if(token == null || token.trim().isEmpty()){
            orderResponse.setSuccess(false);
            orderResponse.setMessage("Not Authorized Login Again");
        }else{
            UserIdResponse userIdResponse = gson.fromJson(jwtServiceImp.decryptToken(token),UserIdResponse.class);
            if (orderServiceImp.userOrder(userIdResponse.getUserId()) == null || orderServiceImp.userOrder(userIdResponse.getUserId()).isEmpty()){
                orderResponse.setSuccess(false);
                orderResponse.setMessage("Error");
            }else{
                orderResponse.setSuccess(true);
                orderResponse.setData(orderServiceImp.userOrder(userIdResponse.getUserId()));
            }
        }
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listOrder(){
        OrderResponse orderResponse = new OrderResponse();
        if (orderServiceImp.listOrder() == null || orderServiceImp.listOrder().isEmpty()){
            orderResponse.setSuccess(false);
            orderResponse.setMessage("Error");
        }else{
            orderResponse.setSuccess(true);
            orderResponse.setData(orderServiceImp.listOrder());
        }
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @PutMapping("/status")
    public ResponseEntity<?> updateOrderStatus(@RequestBody OrderStatusRequest orderStatusRequest){
        OrderResponse orderResponse = new OrderResponse();
        if(orderServiceImp.updateOrderStatus(orderStatusRequest.getOrderId(),orderStatusRequest.getOrderStatus())){
            orderResponse.setSuccess(true);
            orderResponse.setMessage("Status Updated");
        }else{
            orderResponse.setSuccess(false);
            orderResponse.setMessage("Error");
        }
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
}
