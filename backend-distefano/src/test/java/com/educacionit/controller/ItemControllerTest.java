package com.educacionit.controller;

import com.educacionit.entity.Item;
import com.educacionit.entity.Producto;
import com.educacionit.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @SuppressWarnings("unused")
	private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    void testGetAllItems() {
        List<Item> items = Arrays.asList(new Item(new Producto(), 2, 100.50), new Item(new Producto(), 3, 200.75));
        when(itemService.getAll()).thenReturn(items);

        ResponseEntity<List<Item>> response = itemController.getAllItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(itemService, times(1)).getAll();
    }

    @Test
    void testGetItemById() {
        Item item = new Item(new Producto(), 2, 100.50);
        when(itemService.getById(anyInt())).thenReturn(item);

        ResponseEntity<Item> response = itemController.getItemById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(item, response.getBody());
        verify(itemService, times(1)).getById(1);
    }

    @Test
    void testGetItemById_NotFound() {
        when(itemService.getById(anyInt())).thenReturn(null);

        ResponseEntity<Item> response = itemController.getItemById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(itemService, times(1)).getById(1);
    }

    @Test
    void testAddItem() {
        Item item = new Item(new Producto(), 2, 100.50);
        when(itemService.save(any(Item.class))).thenReturn(item);

        ResponseEntity<Item> response = itemController.addItem(item);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(itemService, times(1)).save(item);
    }

    @Test
    void testUpdateItem() throws Exception {
        Item item = new Item(new Producto(), 2, 100.50);
        when(itemService.getById(anyInt())).thenReturn(item);
        when(itemService.update(anyInt(), any(Item.class))).thenReturn(item);

        ResponseEntity<Void> response = itemController.updateItem(1, item);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(itemService, times(1)).getById(1);
        verify(itemService, times(1)).update(1, item);
    }

    @Test
    void testUpdateItem_NotFound() throws Exception {
        when(itemService.getById(anyInt())).thenReturn(null);

        ResponseEntity<Void> response = itemController.updateItem(1, new Item());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(itemService, times(1)).getById(1);
    }

    @Test
    void testDeleteItem() {
        Item item = new Item(new Producto(), 2, 100.50);
        when(itemService.getById(anyInt())).thenReturn(item);
        doNothing().when(itemService).delete(anyInt());

        ResponseEntity<Void> response = itemController.deleteItem(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(itemService, times(1)).getById(1);
        verify(itemService, times(1)).delete(1);
    }

    @Test
    void testDeleteItem_NotFound() {
        when(itemService.getById(anyInt())).thenReturn(null);

        ResponseEntity<Void> response = itemController.deleteItem(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(itemService, times(1)).getById(1);
    }
}
