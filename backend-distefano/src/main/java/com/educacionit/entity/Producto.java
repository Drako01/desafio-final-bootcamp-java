package com.educacionit.entity;

import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de Producto")
@Entity
@Table(name = "producto", indexes = { @Index(name = "categoria_id", 
columnList = "categoria_id") })
public class Producto {

	@Schema(description = "ID del Producto", 
			requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id_producto;

	@Schema(description = "Nombre del producto", 
			requiredMode = Schema.RequiredMode.REQUIRED, example = "Fideos")
	@Column(name = "NOMBRE", nullable = false, length = 100)
	private String nombre;

	@Schema(description = "Descripción del Producto", 
			requiredMode = Schema.RequiredMode.REQUIRED, example = "Fideos de Sémola")
	@Column(name = "DESCRIPCION", nullable = false, length = 255)
	private String descripcion;

	@Schema(description = "Precio del Producto", 
			requiredMode = Schema.RequiredMode.REQUIRED, example = "1050.50")
	@Column(name = "PRECIO", nullable = false)
	private double precio;

	@Schema(description = "Imagen del Producto", 
			requiredMode = Schema.RequiredMode.REQUIRED, example = "imagen.png")
	@Column(name = "IMAGEN", nullable = false)
	private String imagen;

	@Schema(description = "Stock del Producto", 
			requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
	@Column(name = "STOCK", nullable = false)
	private Integer stock;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	@ManyToMany(mappedBy = "productosFav", fetch = FetchType.EAGER)
    private Set<User> usuariosFav = new HashSet<>();
	
	@ManyToMany(mappedBy = "productos", fetch = FetchType.EAGER)
	private Set<Carrito> carritos = new HashSet<>();
	
	public double obtenerSubtotal(int cantidad) {
        return this.precio * cantidad;
    }

}
