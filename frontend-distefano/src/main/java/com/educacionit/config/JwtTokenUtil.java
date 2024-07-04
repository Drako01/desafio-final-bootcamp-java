package com.educacionit.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtil {
	
	@Autowired
    private AppConfig appConfig;

    @SuppressWarnings("deprecation")
	public String generateToken(String subject, String username) {
        return Jwts.builder()
            .setSubject(subject)
            .claim("username", username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
            .signWith(SignatureAlgorithm.HS256, 
            		appConfig.jwtSecret()
            		.getBytes())
            .compact();
    }
}

