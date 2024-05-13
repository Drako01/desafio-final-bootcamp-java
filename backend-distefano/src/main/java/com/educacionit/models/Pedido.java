package com.educacionit.models;

import java.util.Date;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Schema(description = "Modelo de Pedido")
@Entity
@Table(name = "pedido")
public class Pedido {

	@Schema(description = "ID del Pedido", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_pedido;

	@Schema(description = "Fecha en que se realizó el pedido", example = "2024-04-23")
	private Date fecha_pedido;

	@Schema(description = "Estado del pedido", example = "PENDIENTE")
	private String estado;

	@Schema(description = "ID del cliente que realizó el pedido", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@Schema(description = "Detalles de los productos en el pedido")
	@OneToMany(mappedBy = "pedido")
	private List<DetallePedido> detalles;

	public Pedido() {
		super();
	}

	public Pedido(Integer id_pedido, Date fecha_pedido, String estado, Cliente cliente, List<DetallePedido> detalles) {
		super();
		this.id_pedido = id_pedido;
		this.fecha_pedido = fecha_pedido;
		this.estado = estado;
		this.cliente = cliente;
		this.detalles = detalles;
	}

	public int getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(Integer id_pedido) {
		this.id_pedido = id_pedido;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<DetallePedido> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetallePedido> detalles) {
		this.detalles = detalles;
	}

	@Override
	public String toString() {
		return "Pedido [id_pedido=" + id_pedido + ", fecha_pedido=" + fecha_pedido + ", estado=" + estado + ", cliente="
				+ cliente + ", detalles=" + detalles + "]";
	}

}
