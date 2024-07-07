package com.educacionit.service;

import java.security.Key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educacionit.config.AppConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class VerificarTokenService {
    
    @Autowired
    private AppConfig appConfig;
    
    public String verificarToken(String token) {       
        String nombre = null;
        if (token != null) {
            try {
                byte[] keyBytes = Decoders.BASE64.decode(appConfig.jwtSecret());
                Key key = Keys.hmacShaKeyFor(keyBytes);
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody();

                nombre = claims.get("nombre", String.class);              
                
            } catch (Exception e) {
                System.out.println("Error parsing token: " + e.getMessage());
            }
        }

        return nombre;
    }
}
