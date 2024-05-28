package com.educacionit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.educacionit.entity.Categoria;
import com.educacionit.entity.Producto;
import com.educacionit.repository.ProductoRepository;
@SpringBootTest
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;
    private List<Producto> productoList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Categoria categoria = new Categoria("NombreCategoria", "DescripcionCategoria");

        producto = new Producto("NombreProducto", "DescripcionProducto", 1050.50, null, null, categoria);
        productoList = new ArrayList<>();
        productoList.add(producto);
    }

    @Test
    void testSaveProducto() {
        when(productoRepository.save(any())).thenReturn(producto);

        Producto savedProducto = productoService.save(producto);

        assertNotNull(savedProducto);
        assertEquals(producto.getId_producto(), savedProducto.getId_producto());
        assertEquals(producto.getNombre(), savedProducto.getNombre());
        assertEquals(producto.getDescripcion(), savedProducto.getDescripcion());
        assertEquals(producto.getPrecio(), savedProducto.getPrecio());
        assertEquals(producto.getCategoria(), savedProducto.getCategoria());
    }

    @Test
    void testGetByIdProducto() {
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));

        Producto foundProducto = productoService.getById(1);

        assertNotNull(foundProducto);
        assertEquals(producto.getId_producto(), foundProducto.getId_producto());
        assertEquals(producto.getNombre(), foundProducto.getNombre());
        assertEquals(producto.getDescripcion(), foundProducto.getDescripcion());
        assertEquals(producto.getPrecio(), foundProducto.getPrecio());
        assertEquals(producto.getCategoria(), foundProducto.getCategoria());
    }

    @Test
    void testGetByIdProductoNotFound() {
        when(productoRepository.findById(2)).thenReturn(Optional.empty());

        Producto foundProducto = productoService.getById(2);

        assertNull(foundProducto);
    }

    @Test
    void testGetAllProductos() {
        when(productoRepository.findAll()).thenReturn(productoList);

        List<Producto> foundProductos = productoService.getAll();

        assertNotNull(foundProductos);
        assertEquals(productoList.size(), foundProductos.size());
    }

    @Test
    void testUpdateProducto() throws Exception {
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any())).thenReturn(producto);

        Producto updatedProducto = new Producto("NuevoNombreProducto", "NuevaDescripcionProducto", 1000.0, null, null, producto.getCategoria());
        Producto resultProducto = productoService.update(1, updatedProducto);

        assertNotNull(resultProducto);
        assertEquals(updatedProducto.getNombre(), resultProducto.getNombre());
        assertEquals(updatedProducto.getDescripcion(), resultProducto.getDescripcion());
        assertEquals(updatedProducto.getPrecio(), resultProducto.getPrecio());
        assertEquals(updatedProducto.getCategoria(), resultProducto.getCategoria());
    }

    @Test
    void testDeleteProducto() {
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));
        doNothing().when(productoRepository).deleteById(1);

        productoService.delete(1);
    }
}