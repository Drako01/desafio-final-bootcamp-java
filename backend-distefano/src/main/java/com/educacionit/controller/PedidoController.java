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

import com.educacionit.models.Pedido;
import com.educacionit.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Gestión de Pedidos", description = "Endpoints para gestionar pedidos")
public class PedidoController {

	@Autowired
	@Qualifier("pedidoService")
	private PedidoService pedidoService;

	@Operation(summary = "Obtener lista de pedidos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class)) }),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Pedido>> getAllPedidos() {
		List<Pedido> pedidos = pedidoService.getAll();
		return new ResponseEntity<>(pedidos, HttpStatus.OK);
	}

	@Operation(summary = "Obtener un pedido por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Pedido obtenido correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class)) }),
			@ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content) })
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Pedido> getPedidoById(@PathVariable("id") Integer id) {
		Pedido pedido = pedidoService.getById(id);
		if (pedido == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(pedido, HttpStatus.OK);
	}

	@Operation(summary = "Agregar un nuevo pedido")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> addPedido(@RequestBody Pedido pedido) {
		pedidoService.save(pedido);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(summary = "Actualizar un pedido existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Pedido actualizado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> updatePedido(@PathVariable("id") Integer id, @RequestBody Pedido pedido) {
		Pedido currentPedido = pedidoService.getById(id);
		if (currentPedido == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		pedidoService.save(pedido);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "Eliminar un pedido existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Pedido eliminado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletePedido(@PathVariable("id") Integer id) {
		Pedido pedido = pedidoService.getById(id);
		if (pedido == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		pedidoService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
