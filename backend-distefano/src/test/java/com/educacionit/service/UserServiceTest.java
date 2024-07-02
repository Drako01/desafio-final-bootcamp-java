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
import org.springframework.boot.test.context.SpringBootTest;

import com.educacionit.entity.User;
import com.educacionit.repository.UserRepository;
@SpringBootTest
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	private List<User> userList;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		userList = new ArrayList<>();
		userList
				.add(new User());
		userList.add(
				new User());
	}


	@Test
	void testSave() {
		User cliente = new User();
		when(userRepository.save(any(User.class))).thenReturn(cliente);


		User savedCliente = userService.save(cliente);

		assertNotNull(savedCliente);
		assertEquals(cliente, savedCliente);
	}

	@Test
	void testGetById() {
		int clienteId = 1;
		User cliente = userList.get(0);
		when(userRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

		User retrievedCliente = userService.getById(clienteId);

		assertNotNull(retrievedCliente);
		assertEquals(cliente, retrievedCliente);
	}

	@Test
	void testGetByIdNotFound() {
		int clienteId = 3;
		when(userRepository.findById(clienteId)).thenReturn(Optional.empty());

		User retrievedCliente = userService.getById(clienteId);

		assertNull(retrievedCliente);
	}

	@Test
	void testGetAll() {
		when(userRepository.findAll()).thenReturn(userList);

		List<User> retrievedClientes = userService.getAll();

		assertNotNull(retrievedClientes);
		assertEquals(userList, retrievedClientes);
	}

	@Test
	void testUpdate() throws Exception {
		int clienteId = 1;
		User clienteModificado = new User();
		User existingCliente = userList.get(0);
		when(userRepository.findById(clienteId)).thenReturn(Optional.of(existingCliente));
		when(userRepository.save(any(User.class))).thenReturn(clienteModificado);

		User updatedCliente = userService.update(clienteId, clienteModificado);


		assertNotNull(updatedCliente);
		assertEquals(clienteModificado, updatedCliente);
	}


	@Test
	void testUpdateNotFound() {
		int clienteId = 3;
		User clienteModificado = new User();
		when(userRepository.findById(clienteId)).thenReturn(Optional.empty());

		assertThrows(Exception.class, () -> userService.update(clienteId, clienteModificado));
	}

	@Test
	void testDelete() {
		int clienteId = 1;
		userService.delete(clienteId);

		verify(userRepository, times(1)).deleteById(clienteId);
	}

	@Test
	void testGetByEmail() {
		
	}
}