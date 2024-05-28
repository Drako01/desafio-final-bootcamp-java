package com.educacionit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.educacionit.dao.DAOInterface;
import com.educacionit.entity.Carrito;
import com.educacionit.entity.Item;
import com.educacionit.repository.CarritoRepository;
import com.educacionit.repository.ItemRepository;

@Service("carritoService")
public class CarritoService implements DAOInterface<Carrito> {

    @Autowired
    @Qualifier("carritoRepository")
    private CarritoRepository carritoRepository;

    @Autowired
    @Qualifier("itemRepository")
    private ItemRepository itemRepository;

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
            existingCarrito.calcularPrecioTotal();
            
            return carritoRepository.save(existingCarrito);
        } else {
            throw new Exception("El Carrito con ID: " + id + " no Existe en la BD");
        }
    }

    @Override
    public void delete(Integer id) {
        carritoRepository.deleteById(id);
    }

    public void agregarItemAlCarrito(Integer carritoId, Item item) throws Exception {
        Carrito carrito = getById(carritoId);
        if (carrito == null) {
            throw new Exception("El Carrito con ID: " + carritoId + " no Existe en la BD");
        }
        carrito.agregarItem(item);
        itemRepository.save(item);
        carritoRepository.save(carrito);
    }

    public void eliminarItemDelCarrito(Integer carritoId, Integer itemId) throws Exception {
        Carrito carrito = getById(carritoId);
        if (carrito == null) {
            throw new Exception("El Carrito con ID: " + carritoId + " no Existe en la BD");
        }
        Item item = itemRepository.findById(itemId).orElse(null);
        if (item == null || !item.getCarrito().getId_carrito().equals(carritoId)) {
            throw new Exception("El Item con ID: " + itemId + " no Existe en el Carrito con ID: " + carritoId);
        }
        carrito.removerItem(item);
        itemRepository.delete(item);
        carritoRepository.save(carrito);
    }
}
