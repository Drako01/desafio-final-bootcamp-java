package com.educacionit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.educacionit.config.AppConfig;

@Controller
public class IndexController {

    @Autowired
    private AppConfig appConfig;
    
    @GetMapping("/")
    public String index(Model model) {
        String bienvenida = appConfig.bienvenida();
        model.addAttribute("imagePath", "/img/spring.png");
        model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
        model.addAttribute("pageTitle", "FrontEnd | App Spring Boot");
        model.addAttribute("titulo", "Bienvenido " + bienvenida);
        model.addAttribute("subtitulo", "FrontEnd");
        return "index";
    }
}
