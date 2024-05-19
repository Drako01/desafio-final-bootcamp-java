package com.educacionit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.educacionit.models.Cliente;
import com.educacionit.service.ClienteService;

class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

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
    void testGetAllClientesSuccess() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente(1, "Juan", "juan@example.com", null, "123456789", null, null));
        clientes.add(new Cliente(2, "María", "maria@example.com", null, "987654321", null, null));

        when(clienteService.getAll()).thenReturn(clientes);

        ResponseEntity<List<Cliente>> response = clienteController.getAllClientes();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetClienteByIdSuccess() {
        Cliente cliente = new Cliente(1, "Juan", "juan@example.com", null, "123456789", null, null);

        when(clienteService.getById(1)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.getClienteById(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    @Test
    void testGetClienteByIdNotFound() {
        when(clienteService.getById(1)).thenReturn(null);

        ResponseEntity<Cliente> response = clienteController.getClienteById(1);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testAddClienteSuccess() {
        Cliente cliente = new Cliente(1, "Juan", "juan@example.com", null, "123456789", null, null);

        ResponseEntity<Void> response = clienteController.addCliente(cliente);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testUpdateClienteSuccess() {
        Cliente existingCliente = new Cliente(1, "Juan", "juan@example.com", null, "123456789", null, null);
        Cliente updatedCliente = new Cliente(1, "Juan", "juan.perez@example.com", null, "123456789", null, null);

        when(clienteService.getById(1)).thenReturn(existingCliente);

        ResponseEntity<Void> response = clienteController.updateCliente(1, updatedCliente);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateClienteNotFound() {
        Cliente updatedCliente = new Cliente(1, "Juan", "juan.perez@example.com", null, "123456789", null, null);

        when(clienteService.getById(1)).thenReturn(null);

        ResponseEntity<Void> response = clienteController.updateCliente(1, updatedCliente);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteClienteSuccess() {
        Cliente cliente = new Cliente(1, "Juan", "juan@example.com", null, "123456789", null, null);

        when(clienteService.getById(1)).thenReturn(cliente);
        doNothing().when(clienteService).delete(1);

        ResponseEntity<Void> response = clienteController.deleteCliente(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteClienteNotFound() {
        when(clienteService.getById(1)).thenReturn(null);

        ResponseEntity<Void> response = clienteController.deleteCliente(1);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}