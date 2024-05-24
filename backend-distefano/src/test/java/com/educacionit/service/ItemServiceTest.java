package com.educacionit.service;

import com.educacionit.entity.Item;
import com.educacionit.entity.Producto;
import com.educacionit.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveItem() {
        Item item = new Item(new Producto(), 2, 100.50);
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item savedItem = itemService.save(item);

        assertEquals(item, savedItem);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void testGetItemById() {
        Item item = new Item(new Producto(), 2, 100.50);
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item));

        Item foundItem = itemService.getById(1);

        assertEquals(item, foundItem);
        verify(itemRepository, times(1)).findById(1);
    }

    @Test
    void testGetItemById_NotFound() {
        when(itemRepository.findById(anyInt())).thenReturn(Optional.empty());

        Item foundItem = itemService.getById(1);

        assertEquals(null, foundItem);
        verify(itemRepository, times(1)).findById(1);
    }

    @Test
    void testGetAllItems() {
        List<Item> items = Arrays.asList(new Item(new Producto(), 2, 100.50), new Item(new Producto(), 3, 200.75));
        when(itemRepository.findAll()).thenReturn(items);

        List<Item> foundItems = itemService.getAll();

        assertEquals(items, foundItems);
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void testUpdateItem() throws Exception {
        Item existingItem = new Item(new Producto(), 2, 100.50);
        Item updatedItem = new Item(new Producto(), 3, 150.75);
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(existingItem));
        when(itemRepository.save(any(Item.class))).thenReturn(updatedItem);

        Item result = itemService.update(1, updatedItem);

        assertEquals(updatedItem, result);
        verify(itemRepository, times(1)).findById(1);
        verify(itemRepository, times(1)).save(existingItem);
    }

    @Test
    void testUpdateItem_NotFound() {
        Item updatedItem = new Item(new Producto(), 3, 150.75);
        when(itemRepository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            itemService.update(1, updatedItem);
        });

        assertEquals("El Item con ID: 1 no Existe en la BD", exception.getMessage());
        verify(itemRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteItem() {
        doNothing().when(itemRepository).deleteById(anyInt());

        itemService.delete(1);

        verify(itemRepository, times(1)).deleteById(1);
    }
}
