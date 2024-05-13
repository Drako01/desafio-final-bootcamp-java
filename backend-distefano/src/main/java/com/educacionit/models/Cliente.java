package com.educacionit.models;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Schema(description = "Modelo de Cliente")
@Entity
@Table(name = "cliente")
public class Cliente {

	@Schema(description = "ID del Cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_cliente;

	@Schema(description = "Nombre del cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "Juan Perez")
	private String nombre;

	@Schema(description = "Correo electrónico del cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "juan@example.com")
	private String email;

	@Schema(description = "Teléfono del cliente", example = "123456789")
	private String telefono;

	@Schema(description = "Historial de pedidos del cliente")
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> historialPedidos;

	public Cliente() {
		super();
	}

	public Cliente(Integer id_cliente, String nombre, String email, String telefono, List<Pedido> historialPedidos) {
		super();
		this.id_cliente = id_cliente;
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
		this.historialPedidos = historialPedidos;
	}

	public int getId_cliente() {
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<Pedido> getHistorialPedidos() {
		return historialPedidos;
	}

	public void setHistorialPedidos(List<Pedido> historialPedidos) {
		this.historialPedidos = historialPedidos;
	}

	@Override
	public String toString() {
		return "Cliente [id_cliente=" + id_cliente + ", nombre=" + nombre + ", email=" + email + ", telefono="
				+ telefono + ", historialPedidos=" + historialPedidos + "]";
	}

}