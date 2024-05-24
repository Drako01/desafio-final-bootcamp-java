package com.educacionit.entity;

public class HistorialCompras {

	
	private Integer id_detalle;

	
	private Carrito carrito;

	
	private Cliente cliente;

	public HistorialCompras() {
		super();
	}

	public HistorialCompras(Carrito carrito, Cliente cliente) {
		super();
		this.carrito = carrito;
		this.cliente = cliente;
	}

	public Integer getId_detalle() {
		return id_detalle;
	}

	public void setId_detalle(Integer id_detalle) {
		this.id_detalle = id_detalle;
	}

	public Carrito getCarrito() {
		return carrito;
	}

	public void setCarrito(Carrito carrito) {
		this.carrito = carrito;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "HistorialCompras [id_detalle=" + id_detalle + ", carrito=" + carrito + ", cliente=" + cliente + "]";
	}

}
