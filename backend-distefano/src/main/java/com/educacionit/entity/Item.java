package com.educacionit.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de Item")
@Entity
@Table(name = "item", indexes = { @Index(name = "idx_producto_id", columnList = "producto_id"),
		@Index(name = "idx_carrito_id", columnList = "carrito_id") })
public class Item {

	@Schema(description = "ID del Item", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_item;

	@Schema(description = "Producto en el pedido")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "producto_id", nullable = false)
	private Producto producto;

	@Schema(hidden = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "carrito_id", nullable = false)
	private Carrito carrito;

	@Schema(description = "Cantidad del producto en el pedido", example = "2")
	@Column(name = "cantidad", nullable = false)
	private Integer cantidad;

	@Schema(description = "Precio unitario del producto en el pedido", example = "100.50")
	@Column(name = "precio_unitario", nullable = false)
	private double precio_unitario;

	
}
