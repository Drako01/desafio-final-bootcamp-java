package com.educacionit.controller;

import com.educacionit.entity.Carrito;
import com.educacionit.entity.Categoria;
import com.educacionit.entity.Cliente;
import com.educacionit.entity.HistorialCompras;
import com.educacionit.service.HistorialComprasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class HistorialComprasControllerTest {

    @Mock
    private HistorialComprasService historialComprasService;

    @InjectMocks
    private HistorialComprasController historialComprasController;

    private List<HistorialCompras> historialComprasList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        historialComprasList = new ArrayList<>();
        historialComprasList.add(new HistorialCompras(new Carrito(), new Cliente()));
        historialComprasList.add(new HistorialCompras(new Carrito(), new Cliente()));
    }

    @Test
    void testGetAllHistorialCompras() {
        when(historialComprasService.getAll()).thenReturn(historialComprasList);

        ResponseEntity<List<HistorialCompras>> response = historialComprasController.getAllHistorialCompras();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(historialComprasList, response.getBody());
    }

    @Test
    void testGetHistorialComprasById() {
        int historialComprasId = 1;
        HistorialCompras historialCompras = historialComprasList.get(0);
        when(historialComprasService.getById(historialComprasId)).thenReturn(historialCompras);

        ResponseEntity<HistorialCompras> response = historialComprasController.getHistorialComprasById(historialComprasId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(historialCompras, response.getBody());
    }

    @Test
    void testGetHistorialComprasByIdNotFound() {
        int historialComprasId = 3;
        when(historialComprasService.getById(historialComprasId)).thenReturn(null);

        ResponseEntity<HistorialCompras> response = historialComprasController.getHistorialComprasById(historialComprasId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddHistorialCompras() {
        HistorialCompras historialCompras = new HistorialCompras(new Carrito(), new Cliente());

        ResponseEntity<HistorialCompras> response = historialComprasController.addHistorialCompras(historialCompras);

        verify(historialComprasService, times(1)).save(historialCompras);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testUpdateHistorialCompras() throws Exception {
        int historialComprasId = 1;
        HistorialCompras historialComprasModificado = new HistorialCompras(new Carrito(), new Cliente());
        when(historialComprasService.getById(historialComprasId)).thenReturn(historialComprasList.get(0));

        ResponseEntity<Void> response = historialComprasController.updateHistorialCompras(historialComprasId, historialComprasModificado);

        verify(historialComprasService, times(1)).update(historialComprasId, historialComprasModificado);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateHistorialComprasNotFound(){
        when(historialComprasService.getById(1)).thenReturn(null);

        ResponseEntity<HistorialCompras> response = historialComprasController.getHistorialComprasById(1);
            
        assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
    }
    

    @Test
    void testDeleteHistorialCompras() {
        int historialComprasId = 1;
        when(historialComprasService.getById(historialComprasId)).thenReturn(historialComprasList.get(0));

        ResponseEntity<Void> response = historialComprasController.deleteHistorialCompras(historialComprasId);

        verify(historialComprasService, times(1)).delete(historialComprasId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteHistorialComprasNotFound() {
        int historialComprasId = 3;
        when(historialComprasService.getById(historialComprasId)).thenReturn(null);

        ResponseEntity<Void> response = historialComprasController.deleteHistorialCompras(historialComprasId);

        verify(historialComprasService, never()).delete(anyInt());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}