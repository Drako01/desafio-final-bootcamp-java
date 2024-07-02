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

import com.educacionit.entity.User;
import com.educacionit.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Gestión de Usuarios", description = "Endpoints para gestionar usuarios")
public class UserController {

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Operation(summary = "Obtener lista de usuarios")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<User>> getAllUsuarios() {
		List<User> usuarios = userService.getAll();
		return new ResponseEntity<>(usuarios, HttpStatus.OK);
	}

	@Operation(summary = "Obtener un usuario por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario obtenido correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content) })
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<User> getUsuarioById(@PathVariable("id") Integer id) {
		User usuario = userService.getById(id);
		if (usuario == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}

	@Operation(summary = "Agregar un nuevo usuario")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> addUsuario(@RequestBody User usuario) {
		userService.save(usuario);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(summary = "Actualizar un usuario existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> updateUsuario(@PathVariable("id") Integer id, @RequestBody User cliente) {
		User currentUser = userService.getById(id);
		if (currentUser == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userService.save(cliente);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "Eliminar un usuario existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUsuario(@PathVariable("id") Integer id) {
		User usuario = userService.getById(id);
		if (usuario == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
