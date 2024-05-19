package com.educacionit.models;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

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

	@Schema(description = "Password del cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "**********")
	private String password;

	@Schema(description = "Teléfono del cliente", example = "123456789")
	private String telefono;

	@Schema(description = "Nivel de Autenticación del cliente", example = "seller")
	private String level;

	@Schema(description = "Historial de productos favoritos")
	@ManyToMany
	@JoinTable(name = "cliente_producto", joinColumns = @JoinColumn(name = "cliente_id"), inverseJoinColumns = @JoinColumn(name = "producto_id"))
	private List<Producto> productosFav;

	public Cliente() {
		super();
		this.level = "seller";
	}

	public Cliente(Integer id_cliente, String nombre, String email, String password, String telefono, String level,
			List<Producto> productosFav) {
		super();
		this.id_cliente = id_cliente;
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		this.telefono = telefono;
		this.productosFav = productosFav;
		this.level = level;
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Cliente [id_cliente=" + id_cliente + ", nombre=" + nombre + ", email=" + email + ", password="
				+ password + ", telefono=" + telefono + ", level=" + level + ", productosFav=" + productosFav + "]";
	}

}
