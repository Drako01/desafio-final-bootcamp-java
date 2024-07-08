package com.educacionit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.educacionit.config.AppConfig;
import com.educacionit.service.VerificarTokenService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {

	@Autowired
	private VerificarTokenService verificarTokenService;

	@Autowired
	private AppConfig appConfig;

	@GetMapping("/")
	public String index(String authHeader, Model model) {
		String nombre = appConfig.bienvenida();

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);

			String extractedNombre = verificarTokenService.verificarToken(token);

			if (extractedNombre != null && !extractedNombre.isEmpty() && nombre != "Invitado") {
				nombre = extractedNombre;
			}
		}
		log.info("El Usuario " + nombre + " a iniciado Sesión.!");
		model.addAttribute("bienvenida", "Bienvenido " + nombre);
		model.addAttribute("imagePath", "/img/spring.png");
		model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
		model.addAttribute("pageTitle", "FrontEnd | App Spring Boot");
		model.addAttribute("subtitulo", "FrontEnd");

		return "index";
	}

}
