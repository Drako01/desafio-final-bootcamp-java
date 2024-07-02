package com.educacionit.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
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

}
