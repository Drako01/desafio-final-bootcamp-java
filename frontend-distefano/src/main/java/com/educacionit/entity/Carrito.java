package com.educacionit.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Carrito {

	private Integer id_carrito;

	private Date fecha_pedido;

	private String estado;

	private List<Item> items = new ArrayList<>();

	private double precio_total;

	public Carrito() {
		super();
		this.estado = "PENDIENTE";
		this.fecha_pedido = new Date();
	}

	public Carrito(List<Item> items) {
		super();
		this.items = items != null ? items : new ArrayList<>();
		this.estado = "PENDIENTE";
		this.fecha_pedido = new Date();
		calcularPrecioTotal();
	}

	public Integer getId_carrito() {
		return id_carrito;
	}

	public void setId_carrito(Integer id_carrito) {
		this.id_carrito = id_carrito;
	}

	public Date getFecha_pedido() {
		return fecha_pedido;
	}

	public void setFecha_pedido(Date fecha_pedido) {
		this.fecha_pedido = fecha_pedido;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items != null ? items : new ArrayList<>();
		calcularPrecioTotal();
	}

	public double getPrecio_total() {
		return precio_total;
	}

	public void calcularPrecioTotal() {
		this.precio_total = items != null
				? items.stream().mapToDouble(item -> item.getCantidad() * item.getPrecio_unitario()).sum()
				: 0.0;
	}

	public void agregarItem(Item item) {
		items.add(item);
		item.setCarrito(this);
		calcularPrecioTotal();
	}

	public void removerItem(Item item) {
		items.remove(item);
		item.setCarrito(null);
		calcularPrecioTotal();
	}

	@Override
	public String toString() {
		return "Carrito [id_carrito=" + id_carrito + ", fecha_pedido=" + fecha_pedido + ", estado=" + estado
				+ ", items=" + items + ", precio_total=" + precio_total + "]";
	}
}