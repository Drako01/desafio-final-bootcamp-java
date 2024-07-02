package com.educacionit.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.educacionit.entity.Categoria;
import com.educacionit.entity.Producto;
import com.educacionit.service.ProductoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@SpringBootTest
class ProductoControllerTest {

	private MockMvc mockMvc;

	@Mock
	private ProductoService productoService;

	@InjectMocks
	private ProductoController productoController;

	private Producto producto;
	private List<Producto> productoList;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();

		Categoria categoria = new Categoria(null, "Alimentos", "Alimentos para comer");
		producto = new Producto(null, "Fideos", "Fideos de Sémola", 100.0, null, null, categoria, null);
		productoList = new ArrayList<>();
		productoList.add(producto);
		
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void testGetAllProductos() throws Exception {
		when(productoService.getAll()).thenReturn(productoList);

		mockMvc.perform(get("/productos/")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(productoList.size())));
	}

	@Test
	void testGetProductoById() throws Exception {
		when(productoService.getById(1)).thenReturn(producto);

		mockMvc.perform(get("/productos/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.nombre", is("Fideos")))
				.andExpect(jsonPath("$.descripcion", is("Fideos de Sémola"))).andExpect(jsonPath("$.precio", is(100.0)))
				.andExpect(jsonPath("$.categoria.nombre", is("Alimentos")))
				.andExpect(jsonPath("$.categoria.descripcion", is("Alimentos para comer")));
	}

	@Test
	void testGetProductoByIdNotFound() throws Exception {
		when(productoService.getById(2)).thenReturn(null);

		mockMvc.perform(get("/productos/2")).andExpect(status().isNotFound());
	}

	@Test
	void testAddProducto() throws Exception {
		Producto newProducto = new Producto(null, "Arroz", "Arroz blanco", 80.0, null, null, null, null);
		when(productoService.getById(2)).thenReturn(null);

		mockMvc.perform(post("/productos/").contentType(MediaType.APPLICATION_JSON).content(asJsonString(newProducto)))
				.andExpect(status().isCreated());

		verify(productoService, times(1)).save(any(Producto.class));
	}

	@Test
	void testUpdateProductoSuccess() throws Exception {
	    Producto mockProducto = new Producto(null, "Fideos", "Fideos de Sémola", 100.0, null, null, null, null);   
	   
	    when(productoService.getById(1)).thenReturn(mockProducto);	    
	    String updatedProductoJson = "{\"id_producto\":1,\"nombre\":\"Fideos\",\"descripcion\":\"Fideos de Sémola Actualizado\",\"precio\":120.0,\"categoria\":{\"id_categoria\":1,\"nombre\":\"Alimentos\",\"descripcion\":\"Alimentos para comer\"}}";

	    mockMvc.perform(
	            put("/productos/1")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(updatedProductoJson)
	            )
	            .andExpect(status().isOk());
	}




	@Test
	void testUpdateProductoNotFound() throws Exception {
		Producto updatedProducto = new Producto(null, "Arroz", "Arroz blanco", 80.0, null, null, null, null);
		when(productoService.getById(2)).thenReturn(null);

		mockMvc.perform(
				put("/productos/2").contentType(MediaType.APPLICATION_JSON).content(asJsonString(updatedProducto)))
				.andExpect(status().isNotFound());
	}

	@Test
	void testDeleteProducto() throws Exception {
		when(productoService.getById(1)).thenReturn(producto);
		doNothing().when(productoService).delete(1);

		mockMvc.perform(delete("/productos/1")).andExpect(status().isOk());

		verify(productoService, times(1)).delete(1);
	}

	@Test
	void testDeleteProductoNotFound() throws Exception {
		when(productoService.getById(2)).thenReturn(null);

		mockMvc.perform(delete("/productos/2")).andExpect(status().isNotFound());
	}

	private static String asJsonString(final Object obj) throws RuntimeException, JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}
}