package com.educacionit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.educacionit.service.VerificarTokenService;

@Controller
public class IndexController {

    @Autowired
    private VerificarTokenService verificarTokenService;

    @GetMapping("/")
    public String index(String authHeader, Model model) {
        String nombre = "Invitado";

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            String extractedNombre = verificarTokenService.verificarToken(token);
           
            if (extractedNombre != null && !extractedNombre.isEmpty()
            		&& nombre != "Invitado") {
                nombre = extractedNombre;
            }
        }

        model.addAttribute("bienvenida", "Bienvenido " + nombre);
        model.addAttribute("imagePath", "/img/spring.png");
        model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
        model.addAttribute("pageTitle", "FrontEnd | App Spring Boot");
        model.addAttribute("subtitulo", "FrontEnd");
        

        return "index";
    }
}
