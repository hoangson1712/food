package com.example.demo.service;

import com.example.demo.request.LoginUserRequest;
import com.example.demo.request.RegisterUserRequest;

public interface UserService {
    boolean checkEmail(String email);
    boolean registerUser(RegisterUserRequest registerUserRequest);
    String createUserToken(String email);
    boolean loginUser(LoginUserRequest loginUserRequest);
}
