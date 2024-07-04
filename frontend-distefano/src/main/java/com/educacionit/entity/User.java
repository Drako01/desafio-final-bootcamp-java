package com.educacionit.entity;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private Integer id;

	private String nombre;

	private String username;

	private String password;

	private String telefono;

	private Set<Role> roles = new HashSet<>();
	
	public void addRole(Role role) {
		this.roles.add(role);
	}
	
	private Set<Carrito> carrito = new HashSet<>();

	private Set<Producto> productosFav = new HashSet<>();

	
}
