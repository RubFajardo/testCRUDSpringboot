package com.example.testRuben.service;

import com.example.testRuben.model.UserModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final String SECRET = "MI_SECRETO_ULTRA_SEGURO";

    public String generateToken(UserModel user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public String validate(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
