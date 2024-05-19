package com.educacionit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.educacionit.models.Cliente;
import com.educacionit.models.DetallePedido;
import com.educacionit.models.Pedido;
import com.educacionit.repository.PedidoRepository;

class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;
    private List<Pedido> pedidoList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Cliente cliente = new Cliente(1, "NombreCliente", "cliente@correo.com", null, "123456789", null, null);
        DetallePedido detallePedido = new DetallePedido(1, null, null, 2, 10.5, 21.0);
        List<DetallePedido> detalles = new ArrayList<>();
        detalles.add(detallePedido);

        pedido = new Pedido(1, new Date(), "PENDIENTE", cliente, detalles);
        pedidoList = new ArrayList<>();
        pedidoList.add(pedido);
    }

    @Test
    void testSavePedido() {
        when(pedidoRepository.save(any())).thenReturn(pedido);

        Pedido savedPedido = pedidoService.save(pedido);

        assertNotNull(savedPedido);
        assertEquals(pedido.getId_pedido(), savedPedido.getId_pedido());
        assertEquals(pedido.getFecha_pedido(), savedPedido.getFecha_pedido());
        assertEquals(pedido.getEstado(), savedPedido.getEstado());
        assertEquals(pedido.getCliente(), savedPedido.getCliente());
        assertEquals(pedido.getDetalles(), savedPedido.getDetalles());
    }

    @Test
    void testGetByIdPedido() {
        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));

        Pedido foundPedido = pedidoService.getById(1);

        assertNotNull(foundPedido);
        assertEquals(pedido.getId_pedido(), foundPedido.getId_pedido());
        assertEquals(pedido.getFecha_pedido(), foundPedido.getFecha_pedido());
        assertEquals(pedido.getEstado(), foundPedido.getEstado());
        assertEquals(pedido.getCliente(), foundPedido.getCliente());
        assertEquals(pedido.getDetalles(), foundPedido.getDetalles());
    }

    @Test
    void testGetByIdPedidoNotFound() {
        when(pedidoRepository.findById(2)).thenReturn(Optional.empty());

        Pedido foundPedido = pedidoService.getById(2);

        assertNull(foundPedido);
    }

    @Test
    void testGetAllPedidos() {
        when(pedidoRepository.findAll()).thenReturn(pedidoList);

        List<Pedido> foundPedidos = pedidoService.getAll();

        assertNotNull(foundPedidos);
        assertEquals(pedidoList.size(), foundPedidos.size());
    }

    @Test
    void testUpdatePedido() {
        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any())).thenReturn(pedido);

        Pedido updatedPedido = new Pedido(1, new Date(), "PROCESADO", pedido.getCliente(), pedido.getDetalles());
        Pedido resultPedido = pedidoService.update(1, updatedPedido);

        assertNotNull(resultPedido);
        assertEquals(updatedPedido.getFecha_pedido(), resultPedido.getFecha_pedido());
        assertEquals(updatedPedido.getEstado(), resultPedido.getEstado());
        assertEquals(updatedPedido.getCliente(), resultPedido.getCliente());
        assertEquals(updatedPedido.getDetalles(), resultPedido.getDetalles());
    }

    @Test
    void testDeletePedido() {
        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));
        doNothing().when(pedidoRepository).deleteById(1);

        pedidoService.delete(1);
    }
}
