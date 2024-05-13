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
import com.educacionit.models.Cliente;
import com.educacionit.repository.ClienteRepository;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private List<Cliente> clienteList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente(1, "Juan Perez", "juan@example.com", "123456789", new ArrayList<>());
        clienteList = new ArrayList<>();
        clienteList.add(cliente);
    }

    @Test
    void testSaveCliente() {
        when(clienteRepository.save(any())).thenReturn(cliente);

        Cliente savedCliente = clienteService.save(cliente);

        assertNotNull(savedCliente);
        assertEquals(cliente.getId_cliente(), savedCliente.getId_cliente());
        assertEquals(cliente.getNombre(), savedCliente.getNombre());
        assertEquals(cliente.getEmail(), savedCliente.getEmail());
        assertEquals(cliente.getTelefono(), savedCliente.getTelefono());
    }

    @Test
    void testGetByIdCliente() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        Cliente foundCliente = clienteService.getById(1);

        assertNotNull(foundCliente);
        assertEquals(cliente.getId_cliente(), foundCliente.getId_cliente());
        assertEquals(cliente.getNombre(), foundCliente.getNombre());
        assertEquals(cliente.getEmail(), foundCliente.getEmail());
        assertEquals(cliente.getTelefono(), foundCliente.getTelefono());
    }

    @Test
    void testGetByIdClienteNotFound() {
        when(clienteRepository.findById(2)).thenReturn(Optional.empty());

        Cliente foundCliente = clienteService.getById(2);

        assertNull(foundCliente);
    }

    @Test
    void testGetAllClientes() {
        when(clienteRepository.findAll()).thenReturn(clienteList);

        List<Cliente> foundClientes = clienteService.getAll();

        assertNotNull(foundClientes);
        assertEquals(clienteList.size(), foundClientes.size());
    }

    @Test
    void testUpdateCliente() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any())).thenReturn(cliente);

        Cliente updatedCliente = new Cliente(1, "Juan Perez", "juan@example.com", "987654321", new ArrayList<>());
        Cliente resultCliente = clienteService.update(1, updatedCliente);

        assertNotNull(resultCliente);
        assertEquals(updatedCliente.getTelefono(), resultCliente.getTelefono());
    }

    @Test
    void testDeleteCliente() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).deleteById(1);

        clienteService.delete(1);
    }
}
