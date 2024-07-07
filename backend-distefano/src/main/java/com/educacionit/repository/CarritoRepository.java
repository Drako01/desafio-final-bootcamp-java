package com.educacionit.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educacionit.entity.Carrito;

@Repository("carritoRepository")
public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
	 Set<Carrito> findByUserId(Integer userId);
}
