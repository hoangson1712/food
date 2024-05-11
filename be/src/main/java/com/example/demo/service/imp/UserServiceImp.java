package com.example.demo.service.imp;

import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.LoginUserRequest;
import com.example.demo.request.RegisterUserRequest;
import com.example.demo.response.UserIdResponse;
import com.example.demo.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtServiceImp jwtServiceImp;

    private Gson gson = new Gson();

    @Override
    public boolean checkEmail(String email) {
        boolean isSuccess = false;
        if(userRepository.existsByUserEmail(email)){
            isSuccess = true;
        }
        return isSuccess;
    }

    @Override
    public boolean registerUser(RegisterUserRequest registerUserRequest) {
        boolean isSuccess = false;
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setUserName(registerUserRequest.getUserName());
            userEntity.setUserEmail(registerUserRequest.getUserEmail());
            userEntity.setUserPassword(passwordEncoder.encode(registerUserRequest.getUserPassword()));
            userRepository.save(userEntity);
            isSuccess = true;
        }catch (Exception e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return isSuccess;
    }

    @Override
    public String createUserToken(String email) {
        UserEntity userEntity = userRepository.findByUserEmail(email);
        UserIdResponse userIdResponse = new UserIdResponse();
        userIdResponse.setUserId(userEntity.getUserId());
        String data = gson.toJson(userIdResponse);
        return jwtServiceImp.createToken(data);
    }

    @Override
    public boolean loginUser(LoginUserRequest loginUserRequest) {
        boolean isSuccess = false;
        try {
            UserEntity userEntity = userRepository.findByUserEmail(loginUserRequest.getUserEmail());
            if(passwordEncoder.matches(loginUserRequest.getUserPassword(), userEntity.getUserPassword())){
                isSuccess = true;
            }
        }catch (Exception e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return isSuccess;
    }
}
