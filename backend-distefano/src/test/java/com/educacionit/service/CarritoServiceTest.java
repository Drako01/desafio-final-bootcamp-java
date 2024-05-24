package com.educacionit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.educacionit.entity.Carrito;
import com.educacionit.repository.CarritoRepository;

class CarritoServiceTest {

    @Mock
    private CarritoRepository carritoRepository;

    @InjectMocks
    private CarritoService carritoService;

    private List<Carrito> carritoList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carritoList = new ArrayList<>();
        carritoList.add(new Carrito());
        carritoList.add(new Carrito());
    }

    @Test
    void testSave() {
        Carrito carrito = new Carrito();
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        Carrito savedCarrito = carritoService.save(carrito);

        assertNotNull(savedCarrito);
        assertEquals(carrito, savedCarrito);
    }

    @Test
    void testGetById() {
        int carritoId = 1;
        Carrito carrito = carritoList.get(0);
        when(carritoRepository.findById(carritoId)).thenReturn(Optional.of(carrito));

        Carrito retrievedCarrito = carritoService.getById(carritoId);

        assertNotNull(retrievedCarrito);
        assertEquals(carrito, retrievedCarrito);
    }

    @Test
    void testGetByIdNotFound() {
        int carritoId = 3;
        when(carritoRepository.findById(carritoId)).thenReturn(Optional.empty());

        Carrito retrievedCarrito = carritoService.getById(carritoId);

        assertNull(retrievedCarrito);
    }

    @Test
    void testGetAll() {
        when(carritoRepository.findAll()).thenReturn(carritoList);

        List<Carrito> retrievedCarritos = carritoService.getAll();

        assertNotNull(retrievedCarritos);
        assertEquals(carritoList, retrievedCarritos);
    }

    @Test
    void testUpdate() throws Exception {
        int carritoId = 1;
        Carrito carritoModificado = new Carrito();
        Carrito existingCarrito = carritoList.get(0);
        when(carritoRepository.findById(carritoId)).thenReturn(Optional.of(existingCarrito));
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carritoModificado);

        Carrito updatedCarrito = carritoService.update(carritoId, carritoModificado);

        assertNotNull(updatedCarrito);
        assertEquals(carritoModificado, updatedCarrito);
    }

    @Test
    void testUpdateNotFound() {
        int carritoId = 3;
        Carrito carritoModificado = new Carrito();
        when(carritoRepository.findById(carritoId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> carritoService.update(carritoId, carritoModificado));
    }

    @Test
    void testDelete() {
        int carritoId = 1;
        carritoService.delete(carritoId);

        verify(carritoRepository, times(1)).deleteById(carritoId);
    }
}