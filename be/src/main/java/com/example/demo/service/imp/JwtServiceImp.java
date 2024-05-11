package com.example.demo.service.imp;

import com.example.demo.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtServiceImp implements JwtService {
    @Value("${key.token.jwt}")
    private String strKey;

    @Override
    public String createToken(String data) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
        return Jwts.builder().subject(data).signWith(secretKey).compact();
    }

    @Override
    public String decryptToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }
}
