package com.educacionit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.educacionit.dao.DAOInterface;
import com.educacionit.models.Pedido;
import com.educacionit.repository.PedidoRepository;

@Service("pedidoService")
public class PedidoService implements DAOInterface<Pedido> {

    @Autowired
    @Qualifier("pedidoRepository")
    private PedidoRepository pedidoRepository;

    @Override
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido getById(Integer id) {
        Pedido unPedido = pedidoRepository.findById(id).orElse(null);
        return unPedido;
    }

    @Override
    public List<Pedido> getAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido update(Integer id, Pedido pedido) {
        Pedido existingPedido = pedidoRepository.findById(id).orElse(null);
        if (existingPedido != null) {
            existingPedido.setFecha_pedido(pedido.getFecha_pedido());
            existingPedido.setEstado(pedido.getEstado());
            existingPedido.setCliente(pedido.getCliente());
            existingPedido.setDetalles(pedido.getDetalles());
            return pedidoRepository.save(existingPedido);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Integer id) {
        pedidoRepository.deleteById(id);
    }
}
