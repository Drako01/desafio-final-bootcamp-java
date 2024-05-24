package com.educacionit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educacionit.entity.HistorialCompras;

@Repository("historialComprasRepository")
public interface HistorialComprasRepository extends JpaRepository<HistorialCompras, Integer> {

}
