package com.educacionit.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.educacionit.config.AppConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtTokenUtil {
	
	@Autowired
    private AppConfig appConfig;

    public String generateToken(String subject) {
        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
            .signWith(Keys.hmacShaKeyFor(appConfig.jwtSecret()
            		.getBytes()), SignatureAlgorithm.HS256)
            .compact();
    }
}

