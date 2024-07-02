package com.educacionit.controller;

import java.util.List;

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

import com.educacionit.entity.Carrito;
import com.educacionit.entity.Item;
import com.educacionit.service.CarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/carritos")
@Tag(name = "Gestión de Carritos", description = "Endpoints para gestionar carritos")
public class CarritoController {

	@Autowired
	@Qualifier("carritoService")
	private CarritoService carritoService;

	@Operation(summary = "Obtener una lista de todos los carritos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de carritos obtenida correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Carrito.class)) }),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Carrito>> getAllCarritos() {
	    List<Carrito> carritos = carritoService.getAll();
	    return new ResponseEntity<>(carritos, HttpStatus.OK);
	}

	@Operation(summary = "Obtener un carrito por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Carrito obtenido correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Carrito.class)) }),
			@ApiResponse(responseCode = "404", description = "Carrito no encontrado", content = @Content) })
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Carrito> getCarritoById(@PathVariable("id") Integer id) {
		Carrito carrito = carritoService.getById(id);
		if (carrito == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		log.info(carrito.toString());
		return new ResponseEntity<>(carrito, HttpStatus.OK);
	}

	@Operation(summary = "Agregar un nuevo carrito")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Carrito creado exitosamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Carrito> addCarrito(@RequestBody Carrito carrito) {
		carritoService.save(carrito);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(summary = "Actualizar un carrito existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Carrito actualizado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Carrito no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> updateCarrito(@PathVariable("id") Integer id,
			@RequestBody Carrito carritoModificado) throws Exception {
		Carrito currentCarrito = carritoService.getById(id);
		if (currentCarrito == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		carritoService.update(id, carritoModificado);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "Eliminar un carrito existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Carrito eliminado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Carrito no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteCarrito(@PathVariable("id") Integer id) {
		Carrito carrito = carritoService.getById(id);
		try {
			if (carrito == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			carritoService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error al eliminar el carrito", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Agregar un item a un carrito existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Item agregado al carrito exitosamente"),
			@ApiResponse(responseCode = "404", description = "Carrito no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@PostMapping(value = "/{id}/items", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> addItemToCarrito(@PathVariable("id") Integer id, @RequestBody Item item) {
		try {
			carritoService.agregarItemAlCarrito(id, item);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error al agregar el item al carrito", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Eliminar un item de un carrito existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Item eliminado del carrito exitosamente"),
			@ApiResponse(responseCode = "404", description = "Carrito o Item no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@DeleteMapping(value = "/{carritoId}/items/{itemId}")
	public ResponseEntity<Void> removeItemFromCarrito(@PathVariable("carritoId") Integer carritoId,
			@PathVariable("itemId") Integer itemId) {
		try {
			carritoService.eliminarItemDelCarrito(carritoId, itemId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error al eliminar el item del carrito", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
