package com.educacionit.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Schema(description = "Modelo de Detalle de Pedido")
@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

	@Schema(description = "ID del Detalle de Pedido", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_detalle;

	@ManyToOne
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;

	@ManyToOne
	@JoinColumn(name = "producto_id")
	private Producto producto;

	@Schema(description = "Cantidad del producto en el pedido", example = "2")
	private Integer cantidades;

	@Schema(description = "Precio unitario del producto en el pedido", example = "100.50")
	private double precios_unitarios;

	@Schema(description = "Precio total del detalle", example = "201.00")
	private double precio_total;

	public DetallePedido() {
		super();
	}

	public DetallePedido(Integer id_detalle, Pedido pedido, Producto producto, Integer cantidades, double precios_unitarios,
			double precio_total) {
		super();
		this.id_detalle = id_detalle;
		this.pedido = pedido;
		this.producto = producto;
		this.cantidades = cantidades;
		this.precios_unitarios = precios_unitarios;
		this.precio_total = precio_total;
	}

	public int getId_detalle() {
		return id_detalle;
	}

	public void setId_detalle(Integer id_detalle) {
		this.id_detalle = id_detalle;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidades() {
		return cantidades;
	}

	public void setCantidades(Integer cantidades) {
		this.cantidades = cantidades;
	}

	public double getPrecios_unitarios() {
		return precios_unitarios;
	}

	public void setPrecios_unitarios(double precios_unitarios) {
		this.precios_unitarios = precios_unitarios;
	}

	public double getPrecio_total() {
		return precio_total;
	}

	public void setPrecio_total(double precio_total) {
		this.precio_total = precio_total;
	}

	@Override
	public String toString() {
		return "DetallePedido [id_detalle=" + id_detalle + ", pedido=" + pedido + ", producto=" + producto
				+ ", cantidades=" + cantidades + ", precios_unitarios=" + precios_unitarios + ", precio_total="
				+ precio_total + "]";
	}

}
