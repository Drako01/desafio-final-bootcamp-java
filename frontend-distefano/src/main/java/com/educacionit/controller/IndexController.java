package com.educacionit.controller;

import java.security.Key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.educacionit.config.AppConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Controller
public class IndexController {

    @Autowired
    private AppConfig appConfig;

    @GetMapping("/")
    public String index(@RequestHeader(name = "Authorization", required = false) 
                        String authHeader,
                        Model model) {
        String username = "Invitado";
        String token;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            System.out.println("Token obtenido del encabezado de autorización: " + token);

            try {
                Key key = Keys.hmacShaKeyFor(appConfig.jwtSecret().getBytes());
                Claims claims = Jwts.parserBuilder()
                                    .setSigningKey(key)
                                    .build()
                                    .parseClaimsJws(token)
                                    .getBody();

                username = claims.getSubject();  // Obtén el campo 'sub' del token
            } catch (Exception e) {
                System.out.println("Token inválido: " + e.getMessage());
            }
        }

        model.addAttribute("imagePath", "/img/spring.png");
        model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
        model.addAttribute("pageTitle", "FrontEnd | App Spring Boot");
        model.addAttribute("titulo", "Bienvenido " + username);
        model.addAttribute("subtitulo", "FrontEnd");
        return "index";
    }
}
