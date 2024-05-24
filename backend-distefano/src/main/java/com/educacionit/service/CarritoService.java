package com.educacionit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.educacionit.dao.DAOInterface;
import com.educacionit.entity.Carrito;
import com.educacionit.repository.CarritoRepository;

@Service("carritoService")
public class CarritoService implements DAOInterface<Carrito> {

    @Autowired
    @Qualifier("carritoRepository")
    private CarritoRepository carritoRepository;

    @Override
    public Carrito save(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    @Override
    public Carrito getById(Integer id) {
        return carritoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Carrito> getAll() {
        return carritoRepository.findAll();
    }

    @Override
    public Carrito update(Integer id, Carrito carritoModificado) throws Exception {
        Optional<Carrito> existingCarritoOptional = carritoRepository.findById(id);
        
        if (existingCarritoOptional.isPresent()) {
            Carrito existingCarrito = existingCarritoOptional.get();
            existingCarrito.setFecha_pedido(carritoModificado.getFecha_pedido());
            existingCarrito.setEstado(carritoModificado.getEstado());
            
            existingCarrito.setItems(carritoModificado.getItems());
            
            return carritoRepository.save(existingCarrito);
        } else {
            throw new Exception("El Carrito con ID: " + id + " no Existe en la BD");
        }
    }

    @Override
    public void delete(Integer id) {
        carritoRepository.deleteById(id);
    }
}
