package com.educacionit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educacionit.models.Cliente;

@Repository("clienteRepository")
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	Cliente findByEmail(String email);
}
