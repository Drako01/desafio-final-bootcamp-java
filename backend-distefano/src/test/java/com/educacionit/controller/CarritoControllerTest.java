package com.educacionit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.educacionit.entity.Carrito;
import com.educacionit.entity.Item;
import com.educacionit.service.CarritoService;

@SpringBootTest
public class CarritoControllerTest {

    @Mock
    private CarritoService carritoService;

    @InjectMocks
    private CarritoController carritoController;


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
    public void testGetAllCarritos() {
        List<Carrito> carritos = new ArrayList<>();
        carritos.add(new Carrito());
        carritos.add(new Carrito());

        when(carritoService.getAll()).thenReturn(carritos);

        ResponseEntity<List<Carrito>> responseEntity = carritoController.getAllCarritos();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(carritos.size(), responseEntity.getBody().size());
    }

    @Test
    public void testGetCarritoById() {
        Integer carritoId = 1;
        Carrito carrito = new Carrito();
        carrito.setId_carrito(carritoId);

        when(carritoService.getById(carritoId)).thenReturn(carrito);

        ResponseEntity<Carrito> responseEntity = carritoController.getCarritoById(carritoId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(carrito.getId_carrito(), responseEntity.getBody().getId_carrito());
    }

    @Test
    public void testAddCarrito() {
        Carrito carrito = new Carrito();

        ResponseEntity<Carrito> responseEntity = carritoController.addCarrito(carrito);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(carritoService, times(1)).save(carrito);
    }

    @Test
    public void testUpdateCarrito() throws Exception {
        Integer carritoId = 1;
        Carrito carrito = new Carrito();
        carrito.setId_carrito(carritoId);
        Carrito carritoModificado = new Carrito();

        when(carritoService.getById(carritoId)).thenReturn(carrito);

        ResponseEntity<Void> responseEntity = carritoController.updateCarrito(carritoId, carritoModificado);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(carritoService, times(1)).update(carritoId, carritoModificado);
    }

    @Test
    public void testDeleteCarrito() {
        Integer carritoId = 1;
        Carrito carrito = new Carrito();
        carrito.setId_carrito(carritoId);

        when(carritoService.getById(carritoId)).thenReturn(carrito);

        ResponseEntity<Void> responseEntity = carritoController.deleteCarrito(carritoId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(carritoService, times(1)).delete(carritoId);
    }

    @Test
    public void testAddItemToCarrito() throws Exception {
        // Mock data
        Integer carritoId = 1;
        Carrito carrito = new Carrito();
        Item item = new Item();

        when(carritoService.getById(carritoId)).thenReturn(carrito);

        ResponseEntity<Void> responseEntity = carritoController.addItemToCarrito(carritoId, item);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(carritoService, times(1)).agregarItemAlCarrito(carritoId, item);
    }

    @Test
    public void testRemoveItemFromCarrito() throws Exception {
        // Mock data
        Integer carritoId = 1;
        Integer itemId = 1;
        Carrito carrito = new Carrito();        

        when(carritoService.getById(carritoId)).thenReturn(carrito);

        ResponseEntity<Void> responseEntity = carritoController.removeItemFromCarrito(carritoId, itemId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(carritoService, times(1)).eliminarItemDelCarrito(carritoId, itemId);
    }

}
