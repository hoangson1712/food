package com.example.demo.controller;

import com.example.demo.request.FoodRequest;
import com.example.demo.request.LoginUserRequest;
import com.example.demo.request.RegisterUserRequest;
import com.example.demo.response.UserResponse;
import com.example.demo.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserServiceImp userServiceImp;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequest registerUserRequest){
        UserResponse userResponse = new UserResponse();
        if(userServiceImp.checkEmail(registerUserRequest.getUserEmail())){
            userResponse.setSuccess(false);
            userResponse.setMessage("User already exists");
        }else{
            if(userServiceImp.registerUser(registerUserRequest)){
                userResponse.setSuccess(true);
                userResponse.setToken(userServiceImp.createUserToken(registerUserRequest.getUserEmail()));
            }else{
                userResponse.setSuccess(false);
                userResponse.setMessage("Register Fail");
            }
        }
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginUserRequest loginUserRequest){
        UserResponse userResponse = new UserResponse();
        if(!userServiceImp.checkEmail(loginUserRequest.getUserEmail())){
            userResponse.setSuccess(false);
            userResponse.setMessage("User does not exist");
        }else{
            if(!userServiceImp.loginUser(loginUserRequest)){
                userResponse.setSuccess(false);
                userResponse.setMessage("Invalid credentials");
            }else{
                userResponse.setSuccess(true);
                userResponse.setToken(userServiceImp.createUserToken(loginUserRequest.getUserEmail()));
            }
        }
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
