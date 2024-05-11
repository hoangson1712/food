package com.example.demo.service;

public interface JwtService {
    String createToken(String data);
    String decryptToken(String token);
}
