package com.educacionit.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Schema(description = "Modelo de Pedido")
@Entity
@Table(name = "carrito")
public class Carrito {

	public enum Estado {
		PENDIENTE, COMPLETADO, CANCELADO
	}

	@Schema(description = "ID del Carrito", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_carrito;

	@Schema(description = "Fecha en que se realizó el pedido", example = "2024-04-23")
	@Column(name = "fecha_pedido", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha_pedido;

	@Schema(description = "Estado del pedido", example = "PENDIENTE")
	@Enumerated(EnumType.STRING)
	@Column(name = "estado", nullable = false)
	private Estado estado;

	@OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Item> items = new ArrayList<>();

	@Schema(description = "Precio total del detalle", example = "201.00")
	@Column(name = "precio_total", nullable = false)
	private double precio_total;

	public Carrito() {
		this.estado = Estado.PENDIENTE;
		this.fecha_pedido = new Date();
	}

	public Carrito(List<Item> items) {
		this.items = items != null ? items : new ArrayList<>();
		this.estado = Estado.PENDIENTE;
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
