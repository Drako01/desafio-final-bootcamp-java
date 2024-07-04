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
public class HistorialCompras {

	
	private Integer id_detalle;
	
	private Carrito carrito;
	
	private User cliente;

	
}
