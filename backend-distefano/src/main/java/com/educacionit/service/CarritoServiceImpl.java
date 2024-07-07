package com.educacionit.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educacionit.entity.Carrito;
import com.educacionit.repository.CarritoRepository;

@Service("carritoServiceImpl")
public class CarritoServiceImpl implements CarritoServiceInterface {

    @Autowired
    private CarritoRepository carritoRepository;

    @Override
    public Carrito save(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    @Override
    public Carrito findById(Integer id) {
        return carritoRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Carrito> findByUserId(Integer userId) {
        return carritoRepository.findByUserId(userId);
    }
}
