package com.educacionit.entity;

public class Item {

	private Integer id_item;

	private Producto producto;

	private Carrito carrito;

	private Integer cantidad;

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
