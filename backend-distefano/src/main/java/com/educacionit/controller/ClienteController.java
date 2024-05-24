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

import com.educacionit.entity.Cliente;
import com.educacionit.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Gestión de Clientes", description = "Endpoints para gestionar clientes")
public class ClienteController {

	@Autowired
	@Qualifier("clienteService")
	private ClienteService clienteService;

	@Operation(summary = "Obtener lista de clientes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de clientes obtenida correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class)) }),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Cliente>> getAllClientes() {
		List<Cliente> clientes = clienteService.getAll();
		return new ResponseEntity<>(clientes, HttpStatus.OK);
	}

	@Operation(summary = "Obtener un cliente por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cliente obtenido correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class)) }),
			@ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content) })
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Cliente> getClienteById(@PathVariable("id") Integer id) {
		Cliente cliente = clienteService.getById(id);
		if (cliente == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}

	@Operation(summary = "Agregar un nuevo cliente")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> addCliente(@RequestBody Cliente cliente) {
		clienteService.save(cliente);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(summary = "Actualizar un cliente existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> updateCliente(@PathVariable("id") Integer id, @RequestBody Cliente cliente) {
		Cliente currentCliente = clienteService.getById(id);
		if (currentCliente == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		clienteService.save(cliente);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "Eliminar un cliente existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cliente eliminado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteCliente(@PathVariable("id") Integer id) {
		Cliente cliente = clienteService.getById(id);
		if (cliente == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		clienteService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
