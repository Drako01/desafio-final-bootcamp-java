package com.educacionit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educacionit.entity.Producto;
import com.educacionit.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'SELLER')")
@RequestMapping("/productos-listar")
@Tag(name = "Gestión de Productos", description = "Endpoints para gestionar productos")
public class ProductoController {

	@Autowired
	@Qualifier("productoService")
	private ProductoService productoService;

	@Operation(summary = "Obtener lista de productos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class)) }),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Producto>> getAllProductos() {
		List<Producto> productos = productoService.getAll();
		return new ResponseEntity<>(productos, HttpStatus.OK);
	}

	@Operation(summary = "Obtener un producto por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Producto obtenido correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class)) }),
			@ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content) })
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Producto> getProductoById(@PathVariable("id") Integer id) {
		Producto producto = productoService.getById(id);
		if (producto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(producto, HttpStatus.OK);
	}

	@Operation(summary = "Agregar un nuevo producto")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> addProducto(@RequestBody Producto producto) {
		productoService.save(producto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(summary = "Actualizar un producto existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> updateProducto(@PathVariable("id") Integer id, 
			@RequestBody Producto productoModificado) throws Exception {
	    Producto currentProducto = productoService.getById(id);
	    if (currentProducto == null) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    productoService.update(id, productoModificado); 
	    return new ResponseEntity<>(HttpStatus.OK);
	}


	@Operation(summary = "Eliminar un producto existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteProducto(@PathVariable("id") Integer id) {
		Producto producto = productoService.getById(id);
		if (producto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		productoService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
