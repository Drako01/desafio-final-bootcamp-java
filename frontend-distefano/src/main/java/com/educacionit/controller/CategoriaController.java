package com.educacionit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.educacionit.entity.Categoria;

@Controller
public class CategoriaController {
	final Logger logger = LoggerFactory.getLogger(CategoriaController.class);

	@Autowired
	@Qualifier("restTemplateFront")
	private RestTemplate restTemplateFront;

	@Autowired
	private String baseUrl;

	
	
	private void setAuthHeader(RestTemplate restTemplate, String token) {
        restTemplate.getInterceptors().clear();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set("Authorization", "Bearer " + token);
            return execution.execute(request, body);
        });
    }
	

	@GetMapping("/backend/categorias/")
	public String obtenerCategoriasBackend(@RequestHeader(name = "Authorization", required = false) String authHeader,
			Model model) {
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);	
			setAuthHeader(restTemplateFront, token);
		}

		String apiUrlCategorias = baseUrl + "/categorias-listar/";
		Categoria[] categorias = restTemplateFront.getForObject(apiUrlCategorias, Categoria[].class);
		model.addAttribute("imagePath", "/img/spring.png");
		model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
		model.addAttribute("categorias", categorias);
		model.addAttribute("pageTitle", "Categorias | App Spring Boot");
		model.addAttribute("titulo", "Categorias");
		
		return "backend/categorias-table";
	}

	@GetMapping("/backend/categorias/{id}")
	public String detallesCategoriaPorIDBackend(
			@RequestHeader(name = "Authorization", required = false) String authHeader, @PathVariable Integer id,
			Model model) {

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			setAuthHeader(restTemplateFront, token);
		}

		String apiUrl = baseUrl + "/categorias-listar/" + id;
		Categoria categoria = restTemplateFront.getForObject(apiUrl, Categoria.class);

		if (categoria != null) {
			model.addAttribute("categoria", categoria);
			model.addAttribute("imagePath", "/img/spring.png");
			model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
			model.addAttribute("pageTitle", "Detalles de la Categoría");
			model.addAttribute("titulo", "Detalles de la Categoría");
			
			return "backend/categoria-detalle";
		} else {
			model.addAttribute("pageTitle", "Error 404 - La categoría no existe.");
			model.addAttribute("imagePath", "/img/spring.png");
			model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
			model.addAttribute("titulo", "La categoría " + id + " no existe.");
			return "error";
		}
	}

	@PostMapping("/backend/categorias/agregar/")
	public String agregarCategoriaBackend(@RequestHeader(name = "Authorization", required = false) String authHeader,
			@ModelAttribute Categoria nuevaCategoria, Model model) {
		

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			setAuthHeader(restTemplateFront, token);
		}

		String apiUrl = baseUrl + "/categorias-listar/";
		restTemplateFront.postForObject(apiUrl, nuevaCategoria, Categoria.class);
		return "redirect:/backend/categorias/";
	}

	@GetMapping("/backend/categorias/modificar/{id}")
	public String mostrarFormularioModificarCategoria(
			@RequestHeader(name = "Authorization", required = false) String authHeader, @PathVariable Integer id,
			Model model) {

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			setAuthHeader(restTemplateFront, token);
		}

		String apiUrl = baseUrl + "/categorias-listar/" + id;
		Categoria categoria = restTemplateFront.getForObject(apiUrl, Categoria.class);

		if (categoria != null) {
			model.addAttribute("categoria", categoria);
			model.addAttribute("pageTitle", "Modificar Categoría");
			model.addAttribute("titulo", "Modificar Categoría");
			model.addAttribute("imagePath", "/img/spring.png");
			model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
			
			return "backend/modificar-categoria-form";
		} else {
			model.addAttribute("pageTitle", "Error 404 - La categoría no existe.");
			model.addAttribute("titulo", "La categoría " + id + " no existe.");
			return "error";
		}
	}

	@PostMapping("/backend/categorias/modificar/{id}")
	public String modificarCategoriaPorIdBackend(
			@RequestHeader(name = "Authorization", required = false) String authHeader, @PathVariable Integer id,
			@ModelAttribute Categoria categoriaModificada, Model model) {
		

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			setAuthHeader(restTemplateFront, token);
		}

		String apiUrl = baseUrl + "/categorias-listar/" + id;
		Categoria categoriaExistente = restTemplateFront.getForObject(apiUrl, Categoria.class);

		if (categoriaExistente != null) {
			categoriaExistente.setNombre(categoriaModificada.getNombre());
			categoriaExistente.setDescripcion(categoriaModificada.getDescripcion());

			restTemplateFront.put(apiUrl, categoriaExistente);

			return "redirect:/backend/categorias/";
		} else {
			model.addAttribute("pageTitle", "Error - Categoría no encontrada");
			model.addAttribute("mensajeError", "La categoría con ID " + id + " no existe.");
			model.addAttribute("imagePath", "/img/spring.png");
			model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
			
			return "error";
		}
	}

	@GetMapping("/backend/categorias/eliminar/{id}")
	public String eliminarCategoriaBackend(@RequestHeader(name = "Authorization", required = false) String authHeader,
			@PathVariable Integer id, RedirectAttributes attributes) {
	

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			setAuthHeader(restTemplateFront, token);
		}

		String apiUrl = baseUrl + "/categorias-listar/" + id;
		restTemplateFront.delete(apiUrl);

		logger.info("Categoría eliminada con éxito. Redireccionando a la página de categorías.");
		attributes.addFlashAttribute("mensajeSuccess", "Categoría eliminada con éxito");

		return "redirect:/backend/categorias/";
	}

	@GetMapping("/backend/eliminar/error-categoria/")
	public String errorAlEliminarCategoriasBackend(
			@RequestHeader(name = "Authorization", required = false) String authHeader, Model model) {
		

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			setAuthHeader(restTemplateFront, token);
		}

		model.addAttribute("pageTitle", "Error al eliminar categoría");
		model.addAttribute("btnTitle", "Volver a categorías");
		model.addAttribute("mensajeError",
				"No se puede eliminar la categoría porque está asignada a uno o varios productos.");
		model.addAttribute("imagePath", "/img/spring.png");
		model.addAttribute("imageError403", "/img/error-403.jpg");
		model.addAttribute("href", "/backend/categorias/");
		model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
		
		return "backend/error403";
	}

	@GetMapping("/backend/categorias/json")
	@ResponseBody
	public Categoria[] obtenerCategoriasJson(
			@RequestHeader(name = "Authorization", required = false) 
			String authHeader) {
		

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			setAuthHeader(restTemplateFront, token);
		}

		String apiUrlCategorias = baseUrl + "/categorias-listar/";
		return restTemplateFront.getForObject(apiUrlCategorias, Categoria[].class);
	}
}
