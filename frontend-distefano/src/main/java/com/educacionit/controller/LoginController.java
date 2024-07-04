package com.educacionit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController {
	
	
	@Autowired
	@Qualifier("restTemplateFront")
	private RestTemplate restTemplateFront;
	
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("imagePath", "/img/spring.png");
		model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
		model.addAttribute("pageTitle", "Login Page | App Spring Boot");
		model.addAttribute("titulo", "Iniciar Sesión");
		return "login";
	}

	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("imagePath", "/img/spring.png");
		model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
		model.addAttribute("pageTitle", "Signup Page | App Spring Boot");
		model.addAttribute("titulo", "Crear cuenta");
		return "signup";
	}
	
}
