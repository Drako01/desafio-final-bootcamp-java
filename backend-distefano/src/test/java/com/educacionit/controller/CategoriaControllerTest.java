package com.educacionit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.educacionit.models.Categoria;
import com.educacionit.service.CategoriaService;

class CategoriaControllerTest {

	@Mock
	private CategoriaService categoriaService;

	@InjectMocks
	private CategoriaController categoriaController;

	private AutoCloseable closeable;

	@BeforeEach
	void setUp() throws Exception {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
		closeable.close();
	}

	@Test
	void testGetAllCategoriasSuccess() {
		List<Categoria> categorias = new ArrayList<>();
		categorias.add(new Categoria(1, "Categoría 1", "Descripción 1"));
		categorias.add(new Categoria(2, "Categoría 2", "Descripción 2"));

		when(categoriaService.getAll()).thenReturn(categorias);

		ResponseEntity<List<Categoria>> response = categoriaController.getAllCategorias();

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
	}

	@Test
	void testGetCategoriaByIdSuccess() {
		Categoria categoria = new Categoria(1, "Categoría 1", "Descripción 1");

		when(categoriaService.getById(1)).thenReturn(categoria);

		ResponseEntity<Categoria> response = categoriaController.getCategoriaById(1);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(categoria, response.getBody());
	}

	@Test
	void testGetCategoriaByIdNotFound() {
		when(categoriaService.getById(1)).thenReturn(null);

		ResponseEntity<Categoria> response = categoriaController.getCategoriaById(1);

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	void testAddCategoriaSuccess() {
		Categoria categoria = new Categoria(1, "Categoría 1", "Descripción 1");

		ResponseEntity<Categoria> response = categoriaController.addCategoria(categoria);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void testUpdateCategoriaSuccess() {
		Categoria existingCategoria = new Categoria(1, "Categoría 1", "Descripción 1");
		Categoria updatedCategoria = new Categoria(1, "Categoría 1 Actualizada", "Descripción 1 Actualizada");

		when(categoriaService.getById(1)).thenReturn(existingCategoria);

		ResponseEntity<Void> response = categoriaController.updateCategoria(1, updatedCategoria);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testUpdateCategoriaNotFound() {
		Categoria updatedCategoria = new Categoria(1, "Categoría 1 Actualizada", "Descripción 1 Actualizada");

		when(categoriaService.getById(1)).thenReturn(null);

		ResponseEntity<Void> response = categoriaController.updateCategoria(1, updatedCategoria);

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void testDeleteCategoriaSuccess() {
		Categoria categoria = new Categoria(1, "Categoría 1", "Descripción 1");

		when(categoriaService.getById(1)).thenReturn(categoria);
		doNothing().when(categoriaService).delete(1);

		ResponseEntity<Void> response = categoriaController.deleteCategoria(1);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testDeleteCategoriaNotFound() {
		when(categoriaService.getById(1)).thenReturn(null);

		ResponseEntity<Void> response = categoriaController.deleteCategoria(1);

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}