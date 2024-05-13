package com.educacionit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educacionit.models.Producto;

@Repository("productoRepository")
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}
