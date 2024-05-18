package com.educacionit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.educacionit.model.Categoria;
import com.educacionit.model.Producto;

@Controller
public class ProductoController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private String baseUrl;

	@GetMapping("/productos/")
	public String obtenerProductos(Model model) {
		String apiUrl = baseUrl + "/productos/";
		Producto[] productos = restTemplate.getForObject(apiUrl, Producto[].class);

		model.addAttribute("imagePath", "/img/spring.png");
		model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
		model.addAttribute("productos", productos);
		model.addAttribute("pageTitle", "Productos | App Spring Boot");
		model.addAttribute("titulo", "Productos");
		return "productos";
	}

	@GetMapping("/productos/detalles/{id}")
	public String obtenerDetallesProducto(@PathVariable Integer id, Model model) {
		String apiUrl = baseUrl + "/productos/" + id;
		String apiUrlCategorias = baseUrl + "/categorias/";

		Producto producto = restTemplate.getForObject(apiUrl, Producto.class);
		Categoria[] categorias = restTemplate.getForObject(apiUrlCategorias, Categoria[].class);

		if (producto != null) {
			model.addAttribute("producto", producto);
			model.addAttribute("categorias", categorias);
			model.addAttribute("imagePath", "/img/spring.png");
			model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
			model.addAttribute("pageTitle", "Detalles del Producto");
			model.addAttribute("titulo", "Detalles del Producto");
			// Atributos
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

	// Backend
	@GetMapping("/backend/productos/")
	public String obtenerProductosBackend(Model model) {
		String apiUrl = baseUrl + "/productos/";
		String apiUrlCategorias = baseUrl + "/categorias/";

		Producto[] productos = restTemplate.getForObject(apiUrl, Producto[].class);
		Categoria[] categorias = restTemplate.getForObject(apiUrlCategorias, Categoria[].class);

		model.addAttribute("imagePath", "/img/spring.png");
		model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
		model.addAttribute("productos", productos);
		model.addAttribute("categorias", categorias);
		model.addAttribute("pageTitle", "Productos | App Spring Boot");
		model.addAttribute("titulo", "Productos");
		return "backend/productos-table";
	}

	@GetMapping("/backend/productos/modificar/{id}")
	public String mostrarFormularioModificarProducto(@PathVariable Integer id, Model model) {
		String apiUrl = baseUrl + "/productos/";
		String apiUrlCategorias = baseUrl + "/categorias/";
		Producto producto = restTemplate.getForObject(apiUrl + id, Producto.class);
		Categoria[] categorias = restTemplate.getForObject(apiUrlCategorias, Categoria[].class);

		model.addAttribute("imagePath", "/img/spring.png");
		model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
		model.addAttribute("producto", producto);
		model.addAttribute("categorias", categorias);
		model.addAttribute("pageTitle", "Modificar Producto | App Spring Boot");
		model.addAttribute("titulo", "Modificar Producto");

		return "backend/productos-modificar";
	}

	@PostMapping("/backend/productos/modificar/{id}")
	public String actualizarProducto(@PathVariable("id") Integer id, @ModelAttribute Producto productoModificado,
			@RequestParam("categoriaId") Integer categoriaId, Model model) {

		String apiUrl = baseUrl + "/productos/" + id;
		Producto productoExistente = restTemplate.getForObject(apiUrl, Producto.class);

		if (productoExistente != null) {
			productoExistente.setNombre(productoModificado.getNombre());
			productoExistente.setDescripcion(productoModificado.getDescripcion());
			productoExistente.setPrecio(productoModificado.getPrecio());
			productoExistente.setImagen(productoModificado.getImagen());
			productoExistente.setStock(productoModificado.getStock());

			Categoria categoria = new Categoria();
			categoria.setId_categoria(categoriaId);
			productoExistente.setCategoria(categoria);

			restTemplate.put(apiUrl, productoExistente);

			return "redirect:/backend/productos/";
		} else {
			model.addAttribute("pageTitle", "Error - Producto no encontrado");
			model.addAttribute("mensajeError", "El producto con ID " + id + " no existe.");
			return "error";
		}

	}

	@GetMapping("/backend/productos/eliminar/{id}")
	public String eliminarProducto(@PathVariable Integer id, Model model) {
		String apiUrl = baseUrl + "/productos/" + id;
		restTemplate.delete(apiUrl);
		return "redirect:/backend/productos/";
	}

	@PostMapping("/backend/productos/agregar/")
	public String agregarProductoBackend(@ModelAttribute Producto nuevoProducto,
			@RequestParam("categoriaId") Integer categoriaId, Model model) {		

		String apiUrl = baseUrl + "/productos/";
		String apiUrlCategorias = baseUrl + "/categorias/";
		Categoria categoria = restTemplate.getForObject(apiUrlCategorias + categoriaId, Categoria.class);
		nuevoProducto.setCategoria(categoria);		
		restTemplate.postForObject(apiUrl, nuevoProducto, Producto.class);

		return "redirect:/backend/productos/";
	}
	
	@GetMapping("/backend/productos/json")
	@ResponseBody
	public Producto[] obtenerProductosJson() {
	    String apiUrl = baseUrl + "/productos/";
	    return restTemplate.getForObject(apiUrl, Producto[].class);
	}

}
