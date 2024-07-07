package com.educacionit.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de Cliente")
@Entity
@Table(name = "user")
public class User implements UserDetails {

	@Schema(description = "ID del Usuario", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Schema(description = "Nombre del Usuario", requiredMode = Schema.RequiredMode.REQUIRED, example = "Juan Perez")
	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;

	@Schema(description = "Correo electrónico del Usuario", requiredMode = Schema.RequiredMode.REQUIRED, example = "juan@example.com")
	@Column(name = "username", nullable = false, unique = true, length = 100)
	private String username;

	@Schema(description = "Password del Usuario", requiredMode = Schema.RequiredMode.REQUIRED, example = "**********")
	@Column(name = "password", nullable = false, length = 256)
	private String password;

	@Schema(description = "Teléfono del cliente", example = "123456789")
	@Column(name = "telefono", length = 20)
	private String telefono;

	@Schema(description = "Nivel de Autenticación del cliente", example = "ROLE_SELLER")
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public void addRole(Role role) {
		this.roles.add(role);
	}

	@Schema(description = "Modelo de Historial de Compras")
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Carrito> carritos ;

	@Schema(description = "Historial de productos favoritos", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "favoritos", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "producto_id"))
	private Set<Producto> productosFav = new HashSet<>();

	@Transactional
	public void addCarrito(Carrito carrito) {
		this.carritos.add(carrito);
		carrito.setUser(this);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authoritiest = new ArrayList<>();

		for (Role unRole : roles) {
			if (unRole.getName() != null) {
				authoritiest.add(new SimpleGrantedAuthority(unRole.getName()));
			}
		}
		return authoritiest;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
