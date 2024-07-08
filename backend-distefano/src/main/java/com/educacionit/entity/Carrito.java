package com.educacionit.entity;

import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de Carrito de Compras")
@Entity
@Table(name = "carrito")
public class Carrito {

    @Schema(description = "ID del Carrito", 
    		requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Usuario dueño del Carrito", 
    		requiredMode = Schema.RequiredMode.REQUIRED)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Schema(description = "Productos en el Carrito", 
    		requiredMode = Schema.RequiredMode.REQUIRED)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "carrito_productos",
        joinColumns = @JoinColumn(name = "carrito_id"),
        inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private Set<Producto> productos = new HashSet<>();
    
    
    @Schema(description = "Estado del Carrito", 
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado = Estado.PENDIENTE;
    
    public void addProducto(Producto producto) {
        this.productos.add(producto);
        producto.getCarritos().add(this);
    }
}
