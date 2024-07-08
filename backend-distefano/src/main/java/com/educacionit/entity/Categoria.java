package com.educacionit.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	@Column(name = "NOMBRE", nullable = false, length = 100, unique = true)
	private String nombre;

	@Schema(description = "Descripción de la categoría", requiredMode = Schema.RequiredMode.REQUIRED, example = "Productos de almacen")
	@Column(name = "DESCRIPCION", nullable = false, length = 255)
	private String descripcion;

}
