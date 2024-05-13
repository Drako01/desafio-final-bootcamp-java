package com.educacionit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educacionit.models.DetallePedido;

@Repository("detallePedidoRepository")
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

}
