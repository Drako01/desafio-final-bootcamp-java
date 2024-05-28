package com.educacionit.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Schema(description = "Modelo de Item")
@Entity
@Table(name = "item")
public class Item {

	@Schema(description = "ID del Item", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_item;

	@Schema(description = "Producto en el pedido")
	@ManyToOne
	@JoinColumn(name = "producto_id", nullable = false)
	private Producto producto;

	@Schema(hidden = true)
	@ManyToOne
	@JoinColumn(name = "carrito_id", nullable = false)
	private Carrito carrito;

	@Schema(description = "Cantidad del producto en el pedido", example = "2")
	private Integer cantidad;

	@Schema(description = "Precio unitario del producto en el pedido", example = "100.50")
	private double precio_unitario;

	public Item() {
		super();
	}

	public Item(Producto producto, Integer cantidad, double precio_unitario) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
		this.precio_unitario = precio_unitario;
	}

	public Integer getId_item() {
		return id_item;
	}

	public void setId_item(Integer id_item) {
		this.id_item = id_item;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Carrito getCarrito() {
		return carrito;
	}

	public void setCarrito(Carrito carrito) {
		this.carrito = carrito;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio_unitario() {
		return precio_unitario;
	}

	public void setPrecio_unitario(double precio_unitario) {
		this.precio_unitario = precio_unitario;
	}

	@Override
	public String toString() {
		return "Item [id_item=" + id_item + ", producto=" + producto + ", cantidad=" + cantidad + ", precio_unitario="
				+ precio_unitario + "]";
	}
}
