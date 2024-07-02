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
public class Producto {

	private Integer id_producto;

	private String nombre;

	private String descripcion;

	private double precio;

	private String imagen;

	private Integer stock;

	private Categoria categoria;

	
}
