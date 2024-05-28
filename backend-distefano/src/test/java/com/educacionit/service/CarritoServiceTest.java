package com.educacionit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.educacionit.entity.Carrito;
import com.educacionit.entity.Item;
import com.educacionit.entity.Producto;
import com.educacionit.repository.CarritoRepository;
import com.educacionit.repository.ItemRepository;

@SpringBootTest
public class CarritoServiceTest {

    @MockBean
    private CarritoRepository carritoRepository;

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private CarritoService carritoService;

    @SuppressWarnings("unchecked")
	@AfterEach
    public void tearDown() {
        reset(carritoRepository, itemRepository);
    }

    @Test
    public void testSaveCarrito() {
        Carrito carrito = new Carrito();
        when(carritoRepository.save(carrito)).thenReturn(carrito);
        Carrito savedCarrito = carritoService.save(carrito);
        assertNotNull(savedCarrito);
        verify(carritoRepository, times(1)).save(carrito);
    }

    @Test
    public void testGetById() {
        Carrito carrito = new Carrito();
        when(carritoRepository.findById(1)).thenReturn(Optional.of(carrito));
        Carrito foundCarrito = carritoService.getById(1);
        assertNotNull(foundCarrito);
        verify(carritoRepository, times(1)).findById(1);
    }

    @Test
    public void testGetAll() {
        Carrito carrito1 = new Carrito();
        Carrito carrito2 = new Carrito();
        List<Carrito> carritos = Arrays.asList(carrito1, carrito2);
        when(carritoRepository.findAll()).thenReturn(carritos);
        List<Carrito> foundCarritos = carritoService.getAll();
        assertEquals(2, foundCarritos.size());
        verify(carritoRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateCarrito() throws Exception {
        Carrito existingCarrito = new Carrito();
        existingCarrito.setId_carrito(1);
        Carrito modifiedCarrito = new Carrito();
        when(carritoRepository.findById(1)).thenReturn(Optional.of(existingCarrito));
        when(carritoRepository.save(existingCarrito)).thenReturn(existingCarrito);
        Carrito updatedCarrito = carritoService.update(1, modifiedCarrito);
        assertNotNull(updatedCarrito);
        verify(carritoRepository, times(1)).findById(1);
        verify(carritoRepository, times(1)).save(existingCarrito);
    }

    @Test
    public void testUpdateCarrito_NotFound() {
        Carrito modifiedCarrito = new Carrito();
        when(carritoRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(Exception.class, () -> {
            carritoService.update(1, modifiedCarrito);
        });
        assertEquals("El Carrito con ID: 1 no Existe en la BD", exception.getMessage());
    }

    @Test
    public void testDeleteCarrito() {
        carritoService.delete(1);
        verify(carritoRepository, times(1)).deleteById(1);
    }

    @Test
    public void testAgregarItemAlCarrito() throws Exception {
        Carrito carrito = new Carrito();
        carrito.setId_carrito(1);
        Producto producto = new Producto();
        Item item = new Item(producto, 2, 50.0);
        when(carritoRepository.findById(1)).thenReturn(Optional.of(carrito));
        carritoService.agregarItemAlCarrito(1, item);
        verify(carritoRepository, times(1)).findById(1);
        verify(itemRepository, times(1)).save(item);
        verify(carritoRepository, times(1)).save(carrito);
        assertEquals(1, carrito.getItems().size());
    }

    @Test
    public void testAgregarItemAlCarrito_CarritoNoEncontrado() {
        Producto producto = new Producto();
        Item item = new Item(producto, 2, 50.0);
        when(carritoRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(Exception.class, () -> {
            carritoService.agregarItemAlCarrito(1, item);
        });
        assertEquals("El Carrito con ID: 1 no Existe en la BD", exception.getMessage());
    }

    @Test
    public void testEliminarItemDelCarrito() throws Exception {
        Carrito carrito = new Carrito();
        carrito.setId_carrito(1);
        Producto producto = new Producto();
        Item item = new Item(producto, 2, 50.0);
        item.setId_item(1);
        carrito.agregarItem(item);
        when(carritoRepository.findById(1)).thenReturn(Optional.of(carrito));
        when(itemRepository.findById(1)).thenReturn(Optional.of(item));
        carritoService.eliminarItemDelCarrito(1, 1);
        verify(carritoRepository, times(1)).findById(1);
        verify(itemRepository, times(1)).findById(1);
        verify(itemRepository, times(1)).delete(item);
        verify(carritoRepository, times(1)).save(carrito);
        assertEquals(0, carrito.getItems().size());
    }

    @Test
    public void testEliminarItemDelCarrito_CarritoNoEncontrado() {
        when(carritoRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(Exception.class, () -> {
            carritoService.eliminarItemDelCarrito(1, 1);
        });
        assertEquals("El Carrito con ID: 1 no Existe en la BD", exception.getMessage());
    }

    @Test
    public void testEliminarItemDelCarrito_ItemNoEncontrado() {
        Carrito carrito = new Carrito();
        carrito.setId_carrito(1);
        when(carritoRepository.findById(1)).thenReturn(Optional.of(carrito));
        when(itemRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(Exception.class, () -> {
            carritoService.eliminarItemDelCarrito(1, 1);
        });
        assertEquals("El Item con ID: 1 no Existe en el Carrito con ID: 1", exception.getMessage());
    }
}
