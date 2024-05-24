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

import com.educacionit.entity.Cliente;
import com.educacionit.entity.Producto;
import com.educacionit.repository.ClienteRepository;

class ClienteServiceTest {

	@Mock
	private ClienteRepository clienteRepository;

	@InjectMocks
	private ClienteService clienteService;

	private List<Cliente> clienteList;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		clienteList = new ArrayList<>();
		clienteList
				.add(new Cliente("Juan Perez", "juan@example.com", "password1", "123456789", "seller", new Producto()));
		clienteList.add(
				new Cliente("Maria Gomez", "maria@example.com", "password2", "987654321", "seller", new Producto()));
	}


	@Test
	void testSave() {
		Cliente cliente = new Cliente("Pedro Ramirez", "pedro@example.com", "password3", "111222333", "seller",
				new Producto());
		when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);


		Cliente savedCliente = clienteService.save(cliente);

		assertNotNull(savedCliente);
		assertEquals(cliente, savedCliente);
	}

	@Test
	void testGetById() {
		int clienteId = 1;
		Cliente cliente = clienteList.get(0);
		when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

		Cliente retrievedCliente = clienteService.getById(clienteId);

		assertNotNull(retrievedCliente);
		assertEquals(cliente, retrievedCliente);
	}

	@Test
	void testGetByIdNotFound() {
		int clienteId = 3;
		when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

		Cliente retrievedCliente = clienteService.getById(clienteId);

		assertNull(retrievedCliente);
	}

	@Test
	void testGetAll() {
		when(clienteRepository.findAll()).thenReturn(clienteList);

		List<Cliente> retrievedClientes = clienteService.getAll();

		assertNotNull(retrievedClientes);
		assertEquals(clienteList, retrievedClientes);
	}

	@Test
	void testUpdate() throws Exception {
		int clienteId = 1;
		Cliente clienteModificado = new Cliente("Juan Perez", "juan@example.com", "newpassword", "123456789", "buyer",
				new Producto());
		Cliente existingCliente = clienteList.get(0);
		when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(existingCliente));
		when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteModificado);

		Cliente updatedCliente = clienteService.update(clienteId, clienteModificado);


		assertNotNull(updatedCliente);
		assertEquals(clienteModificado, updatedCliente);
	}


	@Test
	void testUpdateNotFound() {
		int clienteId = 3;
		Cliente clienteModificado = new Cliente("Pedro Ramirez", "pedro@example.com", "newpassword", "111222333",
				"buyer", new Producto());
		when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

		assertThrows(Exception.class, () -> clienteService.update(clienteId, clienteModificado));
	}

	@Test
	void testDelete() {
		int clienteId = 1;
		clienteService.delete(clienteId);

		verify(clienteRepository, times(1)).deleteById(clienteId);
	}

	@Test
	void testGetByEmail() {
		String email = "juan@example.com";
		Cliente cliente = clienteList.get(0);
		when(clienteRepository.findByEmail(email)).thenReturn(cliente);

		Cliente retrievedCliente = clienteService.getByEmail(email);

		assertNotNull(retrievedCliente);
		assertEquals(cliente, retrievedCliente);
	}
}