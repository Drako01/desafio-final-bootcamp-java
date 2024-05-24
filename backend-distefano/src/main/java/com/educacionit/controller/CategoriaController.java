package com.educacionit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educacionit.entity.Categoria;
import com.educacionit.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Gestión de Categorias", description = "Endpoints para gestionar categorias")
public class CategoriaController {

	private static final Logger logger = LoggerFactory.getLogger(CategoriaController.class);

	@Autowired
	@Qualifier("categoriaService")
	private CategoriaService categoriaService;

	@Operation(summary = "Obtener una lista de todas las categorias")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de categorias obtenida correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class)) }),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Categoria>> getAllCategorias() {
		List<Categoria> categorias = categoriaService.getAll();
		return new ResponseEntity<>(categorias, HttpStatus.OK);
	}

	@Operation(summary = "Obtener una categoría por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Categoría obtenida correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class)) }),
			@ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content) })
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Categoria> getCategoriaById(@PathVariable("id") Integer id) {
		Categoria categoria = categoriaService.getById(id);
		if (categoria == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		logger.info(categoria.toString());
		return new ResponseEntity<>(categoria, HttpStatus.OK);
	}

	@Operation(summary = "Agregar una nueva categoría")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Categoria> addCategoria(@RequestBody Categoria categoria) {
		categoriaService.save(categoria);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(summary = "Actualizar una categoría existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Categoría actualizada exitosamente"),
			@ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })

	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> updateCategoria(@PathVariable("id") Integer id,
			@RequestBody Categoria categoriaModificada) throws Exception {
		Categoria currentCategoria = categoriaService.getById(id);
		if (currentCategoria == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		categoriaService.update(id, categoriaModificada);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "Eliminar una categoría existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Categoría eliminada exitosamente"),
			@ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteCategoria(@PathVariable("id") Integer id) {
		Categoria categoria = categoriaService.getById(id);
		try {

			if (categoria == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			categoriaService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
