package com.educacionit.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Schema(description = "Modelo de Historial de Compras")
@Entity
@Table(name = "historial_compras")
public class HistorialCompras {

	@Schema(description = "ID del Detalle", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_detalle;

    @Schema(description = "Carrito asociado al historial", requiredMode = Schema.RequiredMode.REQUIRED)
    @ManyToOne
    @JoinColumn(name = "id_carrito")
    private Carrito carrito;

    @Schema(description = "Cliente asociado al historial", requiredMode = Schema.RequiredMode.REQUIRED)
    @ManyToOne
    @JoinColumn(name = "id_cliente")
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
