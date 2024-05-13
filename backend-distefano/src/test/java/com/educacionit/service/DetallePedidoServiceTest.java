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

import com.educacionit.models.DetallePedido;
import com.educacionit.repository.DetallePedidoRepository;

class DetallePedidoServiceTest {

	@Mock
	private DetallePedidoRepository detallePedidoRepository;

	@InjectMocks
	private DetallePedidoService detallePedidoService;

	private DetallePedido detallePedido;
	private List<DetallePedido> detallePedidoList;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		detallePedido = new DetallePedido(1, null, null, 2, 10.5, 21.0);
		detallePedidoList = new ArrayList<>();
		detallePedidoList.add(detallePedido);
	}

	@Test
	void testSaveDetallePedido() {
		when(detallePedidoRepository.save(any())).thenReturn(detallePedido);

		DetallePedido savedDetallePedido = detallePedidoService.save(detallePedido);

		assertNotNull(savedDetallePedido);
		assertEquals(detallePedido.getId_detalle(), savedDetallePedido.getId_detalle());
		assertEquals(detallePedido.getCantidades(), savedDetallePedido.getCantidades());
		assertEquals(detallePedido.getPrecios_unitarios(), savedDetallePedido.getPrecios_unitarios());
		assertEquals(detallePedido.getPrecio_total(), savedDetallePedido.getPrecio_total());
	}

	@Test
	void testGetByIdDetallePedido() {
		when(detallePedidoRepository.findById(1)).thenReturn(Optional.of(detallePedido));

		DetallePedido foundDetallePedido = detallePedidoService.getById(1);

		assertNotNull(foundDetallePedido);
		assertEquals(detallePedido.getId_detalle(), foundDetallePedido.getId_detalle());
		assertEquals(detallePedido.getCantidades(), foundDetallePedido.getCantidades());
		assertEquals(detallePedido.getPrecios_unitarios(), foundDetallePedido.getPrecios_unitarios());
		assertEquals(detallePedido.getPrecio_total(), foundDetallePedido.getPrecio_total());
	}

	@Test
	void testGetByIdDetallePedidoNotFound() {
		when(detallePedidoRepository.findById(2)).thenReturn(Optional.empty());

		DetallePedido foundDetallePedido = detallePedidoService.getById(2);

		assertNull(foundDetallePedido);
	}

	@Test
	void testGetAllDetallePedidos() {
		when(detallePedidoRepository.findAll()).thenReturn(detallePedidoList);

		List<DetallePedido> foundDetallePedidos = detallePedidoService.getAll();

		assertNotNull(foundDetallePedidos);
		assertEquals(detallePedidoList.size(), foundDetallePedidos.size());
	}

	@Test
	void testUpdateDetallePedido() {
		when(detallePedidoRepository.findById(1)).thenReturn(Optional.of(detallePedido));
		when(detallePedidoRepository.save(any())).thenReturn(detallePedido);

		DetallePedido updatedDetallePedido = new DetallePedido(1, null, null, 3, 15.0, 45.0);
		DetallePedido resultDetallePedido = detallePedidoService.update(1, updatedDetallePedido);

		assertNotNull(resultDetallePedido);
		assertEquals(updatedDetallePedido.getCantidades(), resultDetallePedido.getCantidades());
		assertEquals(updatedDetallePedido.getPrecios_unitarios(), resultDetallePedido.getPrecios_unitarios());
		assertEquals(updatedDetallePedido.getPrecio_total(), resultDetallePedido.getPrecio_total());
	}

	@Test
	void testDeleteDetallePedido() {
		when(detallePedidoRepository.findById(1)).thenReturn(Optional.of(detallePedido));
		doNothing().when(detallePedidoRepository).deleteById(1);

		detallePedidoService.delete(1);
	}
}
