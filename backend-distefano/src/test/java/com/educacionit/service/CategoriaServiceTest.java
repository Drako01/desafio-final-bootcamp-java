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

import com.educacionit.entity.Categoria;
import com.educacionit.repository.CategoriaRepository;

class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoria;
    private List<Categoria> categoriaList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoria = new Categoria("Electrónica", "Categoría de productos electrónicos");
        categoriaList = new ArrayList<>();
        categoriaList.add(categoria);
    }

    @Test
    void testSaveCategoria() {
        when(categoriaRepository.save(any())).thenReturn(categoria);

        Categoria savedCategoria = categoriaService.save(categoria);

        assertNotNull(savedCategoria);
        assertEquals(categoria.getId_categoria(), savedCategoria.getId_categoria());
        assertEquals(categoria.getNombre(), savedCategoria.getNombre());
        assertEquals(categoria.getDescripcion(), savedCategoria.getDescripcion());
    }

    @Test
    void testGetByIdCategoria() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));

        Categoria foundCategoria = categoriaService.getById(1);

        assertNotNull(foundCategoria);
        assertEquals(categoria.getId_categoria(), foundCategoria.getId_categoria());
        assertEquals(categoria.getNombre(), foundCategoria.getNombre());
        assertEquals(categoria.getDescripcion(), foundCategoria.getDescripcion());
    }

    @Test
    void testGetByIdCategoriaNotFound() {
        when(categoriaRepository.findById(2)).thenReturn(Optional.empty());

        Categoria foundCategoria = categoriaService.getById(2);

        assertNull(foundCategoria);
    }

    @Test
    void testGetAllCategorias() {
        when(categoriaRepository.findAll()).thenReturn(categoriaList);

        List<Categoria> foundCategorias = categoriaService.getAll();

        assertNotNull(foundCategorias);
        assertEquals(categoriaList.size(), foundCategorias.size());
    }

    @Test
    void testUpdateCategoria() throws Exception {
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(any())).thenReturn(categoria);

        Categoria updatedCategoria = new Categoria("Electrodomésticos", "Categoría de productos para el hogar");
        Categoria resultCategoria = categoriaService.update(1, updatedCategoria);

        assertNotNull(resultCategoria);
        assertEquals(updatedCategoria.getNombre(), resultCategoria.getNombre());
        assertEquals(updatedCategoria.getDescripcion(), resultCategoria.getDescripcion());
    }

    @Test
    void testDeleteCategoria() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        doNothing().when(categoriaRepository).deleteById(1);

        categoriaService.delete(1);
    }
}