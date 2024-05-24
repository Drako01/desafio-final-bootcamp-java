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

import com.educacionit.entity.Carrito;
import com.educacionit.entity.Cliente;
import com.educacionit.entity.HistorialCompras;
import com.educacionit.repository.HistorialComprasRepository;

class HistorialComprasServiceTest {

	@Mock
	private HistorialComprasRepository historialComprasRepository;

	@InjectMocks
	private HistorialComprasService historialComprasService;

	private List<HistorialCompras> historialComprasList;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		historialComprasList = new ArrayList<>();
		historialComprasList.add(new HistorialCompras(new Carrito(), new Cliente()));
		historialComprasList.add(new HistorialCompras(new Carrito(), new Cliente()));
	}

	@Test
	void testSave() {
		HistorialCompras historialCompras = new HistorialCompras(new Carrito(), new Cliente());
		when(historialComprasRepository.save(any(HistorialCompras.class))).thenReturn(historialCompras);

		HistorialCompras savedHistorialCompras = historialComprasService.save(historialCompras);

		assertNotNull(savedHistorialCompras);
		assertEquals(historialCompras, savedHistorialCompras);
	}

	@Test
	void testGetById() {
		int historialComprasId = 1;
		HistorialCompras historialCompras = historialComprasList.get(0);
		when(historialComprasRepository.findById(historialComprasId)).thenReturn(Optional.of(historialCompras));

		HistorialCompras retrievedHistorialCompras = historialComprasService.getById(historialComprasId);

		assertNotNull(retrievedHistorialCompras);
		assertEquals(historialCompras, retrievedHistorialCompras);
	}

	@Test
	void testGetByIdNotFound() {
		int historialComprasId = 3;
		when(historialComprasRepository.findById(historialComprasId)).thenReturn(Optional.empty());

		HistorialCompras retrievedHistorialCompras = historialComprasService.getById(historialComprasId);

		assertNull(retrievedHistorialCompras);
	}

	@Test
	void testGetAll() {
		when(historialComprasRepository.findAll()).thenReturn(historialComprasList);

		List<HistorialCompras> retrievedHistorialCompras = historialComprasService.getAll();

		assertNotNull(retrievedHistorialCompras);
		assertEquals(historialComprasList, retrievedHistorialCompras);
	}

	@Test
	void testUpdate() throws Exception {
		int historialComprasId = 1;
		HistorialCompras historialComprasModificado = new HistorialCompras(new Carrito(), new Cliente());
		HistorialCompras existingHistorialCompras = historialComprasList.get(0);
		when(historialComprasRepository.findById(historialComprasId)).thenReturn(Optional.of(existingHistorialCompras));
		when(historialComprasRepository.save(any(HistorialCompras.class))).thenReturn(historialComprasModificado);

		HistorialCompras updatedHistorialCompras = historialComprasService.update(historialComprasId,
				historialComprasModificado);

		assertNotNull(updatedHistorialCompras);
		assertEquals(historialComprasModificado, updatedHistorialCompras);
	}

	@Test
	void testUpdateNotFound() {
		int historialComprasId = 3;
		HistorialCompras historialComprasModificado = new HistorialCompras(new Carrito(), new Cliente());
		when(historialComprasRepository.findById(historialComprasId)).thenReturn(Optional.empty());

		assertThrows(Exception.class,
				() -> historialComprasService.update(historialComprasId, historialComprasModificado));
	}

	@Test
	void testDelete() {
		int historialComprasId = 1;
		historialComprasService.delete(historialComprasId);

		verify(historialComprasRepository, times(1)).deleteById(historialComprasId);
	}
}