package com.educacionit.controller;

import com.educacionit.entity.Cliente;
import com.educacionit.entity.Producto;
import com.educacionit.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
@SpringBootTest
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private List<Cliente> clienteList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clienteList = new ArrayList<>();
        clienteList.add(new Cliente("Juan Perez", "juan@example.com", "password1", "123456789", "seller", new Producto()));
        clienteList.add(new Cliente("Maria Gomez", "maria@example.com", "password2", "987654321", "seller", new Producto()));
    }

    @Test
    void testGetAllClientes() {
        when(clienteService.getAll()).thenReturn(clienteList);

        ResponseEntity<List<Cliente>> response = clienteController.getAllClientes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteList, response.getBody());
    }

    @Test
    void testGetClienteById() {
        int clienteId = 1;
        Cliente cliente = clienteList.get(0);
        when(clienteService.getById(clienteId)).thenReturn(cliente);


        ResponseEntity<Cliente> response = clienteController.getClienteById(clienteId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    @Test
    void testGetClienteByIdNotFound() {
        int clienteId = 3;
        when(clienteService.getById(clienteId)).thenReturn(null);

        ResponseEntity<Cliente> response = clienteController.getClienteById(clienteId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddCliente() {
        Cliente cliente = new Cliente("Pedro Ramirez", "pedro@example.com", "password3", "111222333", "seller", new Producto());


        ResponseEntity<Void> response = clienteController.addCliente(cliente);

        verify(clienteService, times(1)).save(cliente);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testUpdateCliente() {
        int clienteId = 1;
        Cliente clienteModificado = new Cliente("Juan Perez", "juan@example.com", "newpassword", "123456789", "buyer", new Producto());
        when(clienteService.getById(clienteId)).thenReturn(clienteList.get(0));

        ResponseEntity<Void> response = clienteController.updateCliente(clienteId, clienteModificado);

        verify(clienteService, times(1)).save(clienteModificado);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateClienteNotFound() {
        int clienteId = 3;
        Cliente clienteModificado = new Cliente("Pedro Ramirez", "pedro@example.com", "newpassword", "111222333", "buyer", new Producto());
        when(clienteService.getById(clienteId)).thenReturn(null);


        ResponseEntity<Void> response = clienteController.updateCliente(clienteId, clienteModificado);

        verify(clienteService, never()).save(any(Cliente.class));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteCliente() {
        int clienteId = 1;
        when(clienteService.getById(clienteId)).thenReturn(clienteList.get(0));

        ResponseEntity<Void> response = clienteController.deleteCliente(clienteId);

        verify(clienteService, times(1)).delete(clienteId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteClienteNotFound() {
        int clienteId = 3;
        when(clienteService.getById(clienteId)).thenReturn(null);

        ResponseEntity<Void> response = clienteController.deleteCliente(clienteId);

        verify(clienteService, never()).delete(anyInt());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}