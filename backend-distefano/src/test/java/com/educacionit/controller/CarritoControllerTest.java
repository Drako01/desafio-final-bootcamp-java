package com.educacionit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.educacionit.entity.Carrito;
import com.educacionit.service.CarritoService;

class CarritoControllerTest {

    @Mock
    private CarritoService carritoService;

    @InjectMocks
    private CarritoController carritoController;

    private List<Carrito> carritoList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carritoList = new ArrayList<>();
        carritoList.add(new Carrito());
        carritoList.add(new Carrito());
    }

    @Test
    void testGetAllCarritos() {
        when(carritoService.getAll()).thenReturn(carritoList);

        ResponseEntity<List<Carrito>> response = carritoController.getAllCarritos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(carritoList, response.getBody());
    }

    @Test
    void testGetCarritoById() {
        int carritoId = 1;
        Carrito carrito = carritoList.get(0);
        when(carritoService.getById(carritoId)).thenReturn(carrito);

        ResponseEntity<Carrito> response = carritoController.getCarritoById(carritoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(carrito, response.getBody());
    }

    @Test
    void testGetCarritoByIdNotFound() {
        int carritoId = 3;
        when(carritoService.getById(carritoId)).thenReturn(null);

        ResponseEntity<Carrito> response = carritoController.getCarritoById(carritoId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddCarrito() {
        Carrito carrito = new Carrito();

        ResponseEntity<Carrito> response = carritoController.addCarrito(carrito);

        verify(carritoService, times(1)).save(carrito);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testUpdateCarrito() throws Exception {
        int carritoId = 1;
        Carrito carritoModificado = new Carrito();
        when(carritoService.getById(carritoId)).thenReturn(carritoList.get(0));

        ResponseEntity<Void> response = carritoController.updateCarrito(carritoId, carritoModificado);

        verify(carritoService, times(1)).update(carritoId, carritoModificado);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateCarritoNotFound() throws Exception {
        int carritoId = 3;
        Carrito carritoModificado = new Carrito();
        when(carritoService.getById(carritoId)).thenReturn(null);

        ResponseEntity<Void> response = carritoController.updateCarrito(carritoId, carritoModificado);

        verify(carritoService, never()).update(anyInt(), any(Carrito.class));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteCarrito() {
        int carritoId = 1;
        when(carritoService.getById(carritoId)).thenReturn(carritoList.get(0));

        ResponseEntity<Void> response = carritoController.deleteCarrito(carritoId);

        verify(carritoService, times(1)).delete(carritoId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteCarritoNotFound() {
        int carritoId = 3;
        when(carritoService.getById(carritoId)).thenReturn(null);

        ResponseEntity<Void> response = carritoController.deleteCarrito(carritoId);

        verify(carritoService, never()).delete(anyInt());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}