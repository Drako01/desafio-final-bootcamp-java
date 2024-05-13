package com.educacionit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educacionit.models.Pedido;

@Repository("pedidoRepository")
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
