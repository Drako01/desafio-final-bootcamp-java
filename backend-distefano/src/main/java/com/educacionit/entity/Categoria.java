package com.educacionit.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Schema(description = "Modelo de Categoría")
@Entity
@Table(name = "categoria")
public class Categoria {

	@Schema(description = "ID de la Categoría", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id_categoria;

	@Schema(description = "Nombre de la categoría", requiredMode = Schema.RequiredMode.REQUIRED, example = "Almacen")
	@Column(name = "NOMBRE")
	private String nombre;

	@Schema(description = "Descripción de la categoría", requiredMode = Schema.RequiredMode.REQUIRED, example = "Productos de almacen")
	@Column(name = "DESCRIPCION")
	private String descripcion;

	public Categoria() {
		super();
	}

	public Categoria(String nombre, String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public Integer getId_categoria() {
		return id_categoria;
	}

	public void setId_categoria(Integer id_categoria) {
		this.id_categoria = id_categoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Categoria [id_categoria=" + id_categoria + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}

}
