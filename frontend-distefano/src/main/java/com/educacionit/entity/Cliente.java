package com.educacionit.entity;

import java.util.List;

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
public class Cliente {

	private Integer id_cliente;

	private String nombre;

	private String email;

	private String password;

	private String telefono;
	
	private String level;

	private List<Producto> productosFav;

	
}
