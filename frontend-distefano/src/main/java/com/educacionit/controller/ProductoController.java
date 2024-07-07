package com.educacionit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.educacionit.entity.Categoria;
import com.educacionit.entity.Producto;

@Controller
public class ProductoController {

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

	@GetMapping("/productos/")
	public String obtenerProductos(
			@RequestHeader(name = "Authorization", required = false) 
			String authHeader,
			Model model) {
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            setAuthHeader(restTemplateFront, token);
		};
		String apiUrl = baseUrl + "/productos-listar/";
		Producto[] productos = restTemplateFront.getForObject(apiUrl, Producto[].class);

		model.addAttribute("imagePath", "/img/spring.png");
		model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
		model.addAttribute("productos", productos);
		model.addAttribute("pageTitle", "Productos | App Spring Boot");
		model.addAttribute("titulo", "Nuestros Productos");
		
		return "productos";
	}

	@GetMapping("/productos/detalles/{id}")
	public String obtenerDetallesProducto(@RequestHeader(name = "Authorization", required = false) String authHeader,
			@PathVariable Integer id, Model model) {
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            setAuthHeader(restTemplateFront, token);
		};

		String apiUrl = baseUrl + "/productos-listar/" + id;
		String apiUrlCategorias = baseUrl + "/categorias-listar/";

		Producto producto = restTemplateFront.getForObject(apiUrl, Producto.class);
		Categoria[] categorias = restTemplateFront.getForObject(apiUrlCategorias, Categoria[].class);

		if (producto != null) {
			model.addAttribute("producto", producto);
			model.addAttribute("categorias", categorias);
			model.addAttribute("imagePath", "/img/spring.png");
			model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
			model.addAttribute("pageTitle", "Detalles del Producto");
			model.addAttribute("titulo", "Detalles del Producto");
			model.addAttribute("nombre", producto.getNombre());
			model.addAttribute("descripcion", producto.getDescripcion());
			model.addAttribute("precio", producto.getPrecio());
			model.addAttribute("imagen", producto.getImagen());
			model.addAttribute("stock", producto.getStock());
			model.addAttribute("categoria_id", producto.getCategoria().getId_categoria());
			

			return "products_detail";
		} else {
			return "error";
		}
	}

	@GetMapping("/backend/productos/")
	public String obtenerProductosBackend(@RequestHeader(name = "Authorization", required = false) String authHeader,
			Model model) {
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            setAuthHeader(restTemplateFront, token);
		};
		String apiUrl = baseUrl + "/productos-listar/";
		String apiUrlCategorias = baseUrl + "/categorias-listar/";

		Producto[] productos = restTemplateFront.getForObject(apiUrl, Producto[].class);
		Categoria[] categorias = restTemplateFront.getForObject(apiUrlCategorias, Categoria[].class);

		model.addAttribute("imagePath", "/img/spring.png");
		model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
		model.addAttribute("productos", productos);
		model.addAttribute("categorias", categorias);
		model.addAttribute("pageTitle", "Productos | App Spring Boot");
		model.addAttribute("titulo", "Productos");
		
		return "backend/productos-table";
	}

	@GetMapping("/backend/productos/modificar/{id}")
	public String mostrarFormularioModificarProducto(
			@RequestHeader(name = "Authorization", required = false) String authHeader, @PathVariable Integer id,
			Model model) {
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            setAuthHeader(restTemplateFront, token);
		};
		String apiUrl = baseUrl + "/productos-listar/" + id;
		String apiUrlCategorias = baseUrl + "/categorias-listar/";
		Producto producto = restTemplateFront.getForObject(apiUrl, Producto.class);
		Categoria[] categorias = restTemplateFront.getForObject(apiUrlCategorias, Categoria[].class);

		model.addAttribute("imagePath", "/img/spring.png");
		model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
		model.addAttribute("producto", producto);
		model.addAttribute("categorias", categorias);
		model.addAttribute("pageTitle", "Modificar Producto | App Spring Boot");
		model.addAttribute("titulo", "Modificar Producto");
		

		return "backend/productos-modificar";
	}

	@PostMapping("/backend/productos/modificar/{id}")
	public String actualizarProducto(@RequestHeader(name = "Authorization", required = false) String authHeader,
			@PathVariable("id") Integer id, @ModelAttribute Producto productoModificado,
			@RequestParam("categoriaId") Integer categoriaId, Model model) {

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            setAuthHeader(restTemplateFront, token);
		};

		String apiUrl = baseUrl + "/productos-listar/" + id;
		Producto productoExistente = restTemplateFront.getForObject(apiUrl, Producto.class);

		if (productoExistente != null) {
			productoExistente.setNombre(productoModificado.getNombre());
			productoExistente.setDescripcion(productoModificado.getDescripcion());
			productoExistente.setPrecio(productoModificado.getPrecio());
			productoExistente.setImagen(productoModificado.getImagen());
			productoExistente.setStock(productoModificado.getStock());

			Categoria categoria = new Categoria();
			categoria.setId_categoria(categoriaId);
			productoExistente.setCategoria(categoria);

			restTemplateFront.put(apiUrl, productoExistente);

			return "redirect:/backend/productos/";
		} else {
			model.addAttribute("pageTitle", "Error - Producto no encontrado");
			model.addAttribute("mensajeError", "El producto con ID " + id + " no existe.");
			
			return "error";
		}

	}

	@GetMapping("/backend/productos/eliminar/{id}")
	public String eliminarProducto(@RequestHeader(name = "Authorization", required = false) String authHeader,
			@PathVariable Integer id, Model model) {
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            setAuthHeader(restTemplateFront, token);
		};

		String apiUrl = baseUrl + "/productos-listar/" + id;
		restTemplateFront.delete(apiUrl);
	
		return "redirect:/backend/productos/";
	}

	@PostMapping("/backend/productos/agregar/")
	public String agregarProductoBackend(
			@RequestHeader(name = "Authorization", required = false) 
			String authHeader,
			@ModelAttribute Producto nuevoProducto, @RequestParam("categoriaId") Integer categoriaId, Model model) {
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            setAuthHeader(restTemplateFront, token);
		};
		

		String apiUrl = baseUrl + "/productos-listar/";
		String apiUrlCategorias = baseUrl + "/categorias-listar/";
		Categoria categoria = restTemplateFront.getForObject(apiUrlCategorias + categoriaId, Categoria.class);
		nuevoProducto.setCategoria(categoria);
		restTemplateFront.postForObject(apiUrl, nuevoProducto, Producto.class);

		
		return "redirect:/backend/productos/";
	}

	@GetMapping("/backend/productos/json")
	@ResponseBody
	public Producto[] obtenerProductosJson(@RequestHeader(name = "Authorization", 
	required = false) 
	String authHeader) {
		String apiUrl = baseUrl + "/productos-listar/";
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            setAuthHeader(restTemplateFront, token);
		};
		return restTemplateFront.getForObject(apiUrl, Producto[].class);
	}

	
}
