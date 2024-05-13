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

import com.educacionit.models.Pedido;
import com.educacionit.service.PedidoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class PedidoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    private Pedido pedido;
    private List<Pedido> pedidoList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();

        pedido = new Pedido(1, null, "PENDIENTE", null, null);
        pedidoList = new ArrayList<>();
        pedidoList.add(pedido);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetAllPedidos() throws Exception {
        when(pedidoService.getAll()).thenReturn(pedidoList);

        mockMvc.perform(
        		get("/pedidos/"))
        .andExpect(status().isOk());
    }

    @Test
    void testGetPedidoById() throws Exception {
        when(pedidoService.getById(1)).thenReturn(pedido);

        mockMvc.perform(
        		get("/pedidos/1"))
        .andExpect(status().isOk());
    }

    @Test
    void testAddPedido() throws Exception {
        mockMvc.perform(
        		post("/pedidos/")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(asJsonString(pedido)))
                .andExpect(status().isCreated());

        verify(pedidoService, times(1)).save(any(Pedido.class));
    }

    @Test
    void testUpdatePedido() throws Exception {
        when(pedidoService.getById(1)).thenReturn(pedido);

        mockMvc.perform(
        		put("/pedidos/1")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(asJsonString(pedido))
        		)
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePedido() throws Exception {
        when(pedidoService.getById(1)).thenReturn(pedido);
        doNothing().when(pedidoService).delete(1);

        mockMvc.perform(
        		delete("/pedidos/1"))
        .andExpect(status().isOk());

        verify(pedidoService, times(1)).delete(1);
    }

    private static String asJsonString(final Object obj) throws RuntimeException, JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}
}
