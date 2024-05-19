package com.educacionit.model;

import java.util.List;

public class Cliente {

	private Integer id_cliente;

	private String nombre;

	private String email;

	private String password;

	private String telefono;

	private List<Producto> productosFav;

	public Cliente() {
		super();
	}

	public Cliente(Integer id_cliente, String nombre, String email, String password, String telefono,
			List<Producto> productosFav) {
		super();
		this.id_cliente = id_cliente;
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		this.telefono = telefono;
		this.productosFav = productosFav;
	}

	public Integer getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(Integer id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<Producto> getProductosFav() {
		return productosFav;
	}

	public void setProductosFav(List<Producto> productosFav) {
		this.productosFav = productosFav;
	}

	@Override
	public String toString() {
		return "Cliente [id_cliente=" + id_cliente + ", nombre=" + nombre + ", email=" + email + ", password="
				+ password + ", telefono=" + telefono + ", productosFav=" + productosFav + "]";
	}
}
