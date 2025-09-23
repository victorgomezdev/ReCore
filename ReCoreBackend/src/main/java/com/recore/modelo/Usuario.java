package com.recore.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Módulo 4 - Backend avanzado
 * Clase 11 - ORM con JPA/Hibernate
 */
@Entity
@Table(name = "usuarios")
public class Usuario extends EntidadBase implements UserDetails {

	@Column(name = "nombre", length = 100, nullable = false)
	private String nombre;

	@Column(name = "apellido", length = 100, nullable = false)
	private String apellido;

	@Column(name = "email", length = 100, nullable = false, unique = true)
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "El email debe tener un formato válido")
	private String email;

	@Column(name = "password", nullable = false, length = 255)
	private String password;

	@Column(name = "es_admin", columnDefinition = "TINYINT(1)")
	private Boolean esAdmin;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "usuario"})
	private List<Reserva> reservas;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "usuario"})
	private List<Puntuacion> puntuaciones;

	// Constructor por defecto
	public Usuario() {
		this.esAdmin = false;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
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

	public Boolean getEsAdmin() {
		return esAdmin;
	}

	public void setEsAdmin(Boolean esAdmin) {
		this.esAdmin = esAdmin;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	public List<Puntuacion> getPuntuaciones() {
		return puntuaciones;
	}

	public void setPuntuaciones(List<Puntuacion> puntuaciones) {
		this.puntuaciones = puntuaciones;
	}

	// Implementación de UserDetails para Spring Security
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (esAdmin != null && esAdmin) {
			return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getUsername() {
		return email;
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