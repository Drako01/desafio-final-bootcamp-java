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

import com.educacionit.models.DetallePedido;
import com.educacionit.service.DetallePedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/detalles-pedido")
@Tag(name = "Gestión de Detalles de Pedido", description = "Endpoints para gestionar detalles de pedido")
public class DetallePedidoController {

	@Autowired
	@Qualifier("detallePedidoService")
	private DetallePedidoService detallePedidoService;

	@Operation(summary = "Obtener lista de detalles de pedido")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de detalles de pedido obtenida correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = DetallePedido.class)) }),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<DetallePedido>> getAllDetallesPedido() {
		List<DetallePedido> detallesPedido = detallePedidoService.getAll();
		return new ResponseEntity<>(detallesPedido, HttpStatus.OK);
	}

	@Operation(summary = "Obtener un detalle de pedido por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Detalle de pedido obtenido correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = DetallePedido.class)) }),
			@ApiResponse(responseCode = "404", description = "Detalle de pedido no encontrado", content = @Content) })
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<DetallePedido> getDetallePedidoById(@PathVariable("id") Integer id) {
		DetallePedido detallePedido = detallePedidoService.getById(id);
		if (detallePedido == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(detallePedido, HttpStatus.OK);
	}

	@Operation(summary = "Agregar un nuevo detalle de pedido")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Detalle de pedido creado exitosamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> addDetallePedido(@RequestBody DetallePedido detallePedido) {
		detallePedidoService.save(detallePedido);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(summary = "Actualizar un detalle de pedido existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Detalle de pedido actualizado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Detalle de pedido no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> updateDetallePedido(@PathVariable("id") Integer id,
			@RequestBody DetallePedido detallePedido) {
		DetallePedido currentDetallePedido = detallePedidoService.getById(id);
		if (currentDetallePedido == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		detallePedidoService.save(detallePedido);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "Eliminar un detalle de pedido existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Detalle de pedido eliminado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Detalle de pedido no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteDetallePedido(@PathVariable("id") Integer id) {
		DetallePedido detallePedido = detallePedidoService.getById(id);
		if (detallePedido == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		detallePedidoService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
