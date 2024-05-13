package com.educacionit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.educacionit.models.DetallePedido;
import com.educacionit.service.DetallePedidoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class DetallePedidoControllerTest {

	private MockMvc mockMvc;

	@Mock
	private DetallePedidoService detallePedidoService;

	@InjectMocks
	private DetallePedidoController detallePedidoController;

	private DetallePedido detallePedido;
	private List<DetallePedido> detallePedidoList;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(detallePedidoController).build();

		detallePedido = new DetallePedido(1, null, null, 2, 100.50, 201.00);
		detallePedidoList = new ArrayList<>();
		detallePedidoList.add(detallePedido);
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void testGetAllDetallesPedido() throws Exception {
		when(detallePedidoService.getAll()).thenReturn(detallePedidoList);

		mockMvc.perform(get("/detalles-pedido/")).andExpect(status().isOk());
	}

	@Test
	void testGetDetallePedidoById() throws Exception {
		when(detallePedidoService.getById(1)).thenReturn(detallePedido);

		mockMvc.perform(get("/detalles-pedido/1")).andExpect(status().isOk());
	}

	@Test
	void testAddDetallePedido() throws Exception {
		mockMvc.perform(
				post("/detalles-pedido/").contentType(MediaType.APPLICATION_JSON).content(asJsonString(detallePedido)))
				.andExpect(status().isCreated());

		verify(detallePedidoService, times(1)).save(any(DetallePedido.class));
	}

	@Test
	void testUpdateDetallePedido() throws Exception {
		when(detallePedidoService.getById(1)).thenReturn(detallePedido);

		mockMvc.perform(
				put("/detalles-pedido/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(detallePedido)))
				.andExpect(status().isOk());
	}

	@Test
	void testDeleteDetallePedido() throws Exception {
		when(detallePedidoService.getById(1)).thenReturn(detallePedido);
		doNothing().when(detallePedidoService).delete(1);

		mockMvc.perform(delete("/detalles-pedido/1")).andExpect(status().isOk());

		verify(detallePedidoService, times(1)).delete(1);
	}

	private static String asJsonString(final Object obj) throws RuntimeException, JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}
}
