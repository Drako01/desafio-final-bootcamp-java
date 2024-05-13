package com.educacionit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.educacionit.dao.DAOInterface;
import com.educacionit.models.DetallePedido;
import com.educacionit.repository.DetallePedidoRepository;

@Service("detallePedidoService")
public class DetallePedidoService implements DAOInterface<DetallePedido> {

    @Autowired
    @Qualifier("detallePedidoRepository")
    private DetallePedidoRepository detallePedidoRepository;

    @Override
    public DetallePedido save(DetallePedido detallePedido) {
        return detallePedidoRepository.save(detallePedido);
    }

    @Override
    public DetallePedido getById(Integer id) {
        DetallePedido unDetallePedido = detallePedidoRepository.findById(id).orElse(null);
        return unDetallePedido;
    }

    @Override
    public List<DetallePedido> getAll() {
        return detallePedidoRepository.findAll();
    }

    @Override
    public DetallePedido update(Integer id, DetallePedido detallePedido) {
        DetallePedido existingDetallePedido = detallePedidoRepository.findById(id).orElse(null);
        if (existingDetallePedido != null) {
            existingDetallePedido.setPedido(detallePedido.getPedido());
            existingDetallePedido.setProducto(detallePedido.getProducto());
            existingDetallePedido.setCantidades(detallePedido.getCantidades());
            existingDetallePedido.setPrecios_unitarios(detallePedido.getPrecios_unitarios());
            existingDetallePedido.setPrecio_total(detallePedido.getPrecio_total());
            return detallePedidoRepository.save(existingDetallePedido);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Integer id) {
        detallePedidoRepository.deleteById(id);
    }
}
