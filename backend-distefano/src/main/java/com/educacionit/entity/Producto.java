package com.educacionit.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Schema(description = "Modelo de Producto")
@Entity
@Table(name = "producto")
public class Producto {

    @Schema(description = "ID del Producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id_producto;

    @Schema(description = "Nombre del producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "Fideos")
    @Column(name = "NOMBRE")
    private String nombre;

    @Schema(description = "Descripción del Producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "Fideos de Sémola")
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Schema(description = "Precio del Producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "1050.50")
    @Column(name = "PRECIO")
    private double precio;

    @Schema(description = "Imagen del Producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "imagen.png")
    @Column(name = "IMAGEN")
    private String imagen;

    @Schema(description = "Stock del Producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @Column(name = "STOCK")
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;


    public Producto() {
        super();
    }

    public Producto(String nombre, String descripcion, double precio, String imagen, Integer stock,
                    Categoria categoria) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.stock = stock;
        this.categoria = categoria;
    }

    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    
    @Override
    public String toString() {
        return "Producto [id_producto=" + id_producto + ", nombre=" + nombre + ", descripcion=" + descripcion
                + ", precio=" + precio + ", imagen=" + imagen + ", stock=" + stock + ", categoria=" + categoria + "]";
    }
}
