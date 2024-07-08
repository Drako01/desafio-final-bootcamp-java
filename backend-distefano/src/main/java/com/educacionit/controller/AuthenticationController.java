package com.educacionit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educacionit.auth.AuthResponse;
import com.educacionit.dto.LoginRequest;
import com.educacionit.dto.RegisterRequest;
import com.educacionit.entity.User;
import com.educacionit.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación de Usuarios", description = "Endpoints para al autenticación de Usuarios")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@Operation(summary = "Registración del Usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User successfully registered", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
			@ApiResponse(responseCode = "409", description = "Conflict: User already exists", content = @Content) })
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> registracion(@RequestBody RegisterRequest registracion) {
		try {
			AuthResponse response = authenticationService.register(registracion);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@Operation(summary = "Autenticación del Usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Authentication successful", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials", content = @Content) })
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> autenticacion(@RequestBody LoginRequest request) {
		try {
			AuthResponse response = authenticationService.login(request);
            return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@Operation(summary = "Logout del Usuario")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Logout successful", content = @Content) })
	@GetMapping("/logout")
	public ResponseEntity<Void> logout() {
		SecurityContextHolder.clearContext();
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
