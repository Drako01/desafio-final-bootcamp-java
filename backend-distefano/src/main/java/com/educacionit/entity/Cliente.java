package com.educacionit.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Schema(description = "Modelo de Cliente")
@Entity
@Table(name = "cliente")
public class Cliente {

    @Schema(description = "ID del Cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cliente;

    @Schema(description = "Nombre del cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "Juan Perez")
    private String nombre;

    @Schema(description = "Correo electrónico del cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "juan@example.com")
    private String email;

    @Schema(description = "Password del cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "**********")
    private String password;

    @Schema(description = "Teléfono del cliente", example = "123456789")
    private String telefono;

    @Schema(description = "Nivel de Autenticación del cliente", example = "seller")
    private String level;

    @Schema(description = "Historial de productos favoritos", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto productosFav;

    public Cliente() {
        super();
        this.level = "seller";
    }

    public Cliente(String nombre, String email, String password, String telefono, String level, Producto productosFav) {
        super();
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.level = level;
        this.productosFav = productosFav;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Producto getProductosFav() {
        return productosFav;
    }

    public void setProductosFav(Producto productosFav) {
        this.productosFav = productosFav;
    }

    @Override
    public String toString() {
        return "Cliente [id_cliente=" + id_cliente + ", nombre=" + nombre + ", email=" + email + ", password="
                + password + ", telefono=" + telefono + ", level=" + level + ", productosFav=" + productosFav + "]";
    }
}
