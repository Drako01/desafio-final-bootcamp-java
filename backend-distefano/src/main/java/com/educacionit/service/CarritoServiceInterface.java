package com.educacionit.service;

import java.util.Set;

import com.educacionit.entity.Carrito;

public interface CarritoServiceInterface {

    Carrito save(Carrito carrito);

    Carrito findById(Integer id);

    Set<Carrito> findByUserId(Integer userId);
}
