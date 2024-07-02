package com.educacionit.entity;

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
public class Item {

	private Integer id_item;

	private Producto producto;

	private Carrito carrito;

	private Integer cantidad;

	private double precio_unitario;

	
}
