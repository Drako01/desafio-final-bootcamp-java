package com.educacionit.entity;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Carrito {

	private Integer id;

	private User user;

	private Set<Producto> productos = new HashSet<>();

}
