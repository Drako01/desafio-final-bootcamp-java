package com.educacionit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CarritoController {
	final Logger logger = LoggerFactory.getLogger(CategoriaController.class);
	/*
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private String baseUrl;
	*/
	@GetMapping("/carrito/")
	public String obtenerCarritoDeCompras(Model model) {
		

		model.addAttribute("imagePath", "/img/spring.png");
		model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");		
		model.addAttribute("pageTitle", "Carrito de Compras | App Spring Boot");
		model.addAttribute("titulo", "Carrito de Compras");
		return "carrito";
	}

}
