package CatsPrograming.ReCore.models.Core;

import java.time.LocalDate;

import CatsPrograming.ReCore.models.Personas.Persona;

public class Usuario {
	private Integer id;
	private Persona persona;
	private String email;
	private String passwordHash;
	private boolean activo;
	private LocalDate fechaRegistro;
	private LocalDate fechaUltimoAcceso;
	private Integer intentosLogin;
	private LocalDate bloqueadoHasta;
	private String tokenVerificacion;
	private String tokenResetPassword;
	private LocalDate fechaExpiraToken;

	public Usuario(Integer id, Persona persona, String email, String passwordHash, boolean activo,
			LocalDate fechaRegistro, LocalDate fechaUltimoAcceso, Integer intentosLogin, LocalDate bloqueadoHasta,
			String tokenVerificacion, String tokenResetPassword, LocalDate fechaExpiraToken) {
		this.id = id;
		this.persona = persona;
		this.email = email;
		this.passwordHash = passwordHash;
		this.activo = activo;
		this.fechaRegistro = fechaRegistro;
		this.fechaUltimoAcceso = fechaUltimoAcceso;
		this.intentosLogin = intentosLogin;
		this.bloqueadoHasta = bloqueadoHasta;
		this.tokenVerificacion = tokenVerificacion;
		this.tokenResetPassword = tokenResetPassword;
		this.fechaExpiraToken = fechaExpiraToken;
	}

	public Usuario(Persona persona, String email, String passwordHash, boolean activo, LocalDate fechaRegistro,
			LocalDate fechaUltimoAcceso, Integer intentosLogin, LocalDate bloqueadoHasta, String tokenVerificacion,
			String tokenResetPassword, LocalDate fechaExpiraToken) {
		this.persona = persona;
		this.email = email;
		this.passwordHash = passwordHash;
		this.activo = activo;
		this.fechaRegistro = fechaRegistro;
		this.fechaUltimoAcceso = fechaUltimoAcceso;
		this.intentosLogin = intentosLogin;
		this.bloqueadoHasta = bloqueadoHasta;
		this.tokenVerificacion = tokenVerificacion;
		this.tokenResetPassword = tokenResetPassword;
		this.fechaExpiraToken = fechaExpiraToken;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public LocalDate getFechaUltimoAcceso() {
		return fechaUltimoAcceso;
	}

	public void setFechaUltimoAcceso(LocalDate fechaUltimoAcceso) {
		this.fechaUltimoAcceso = fechaUltimoAcceso;
	}

	public Integer getIntentosLogin() {
		return intentosLogin;
	}

	public void setIntentosLogin(Integer intentosLogin) {
		this.intentosLogin = intentosLogin;
	}

	public LocalDate getBloqueadoHasta() {
		return bloqueadoHasta;
	}

	public void setBloqueadoHasta(LocalDate bloqueadoHasta) {
		this.bloqueadoHasta = bloqueadoHasta;
	}

	public String getTokenVerificacion() {
		return tokenVerificacion;
	}

	public void setTokenVerificacion(String tokenVerificacion) {
		this.tokenVerificacion = tokenVerificacion;
	}

	public String getTokenResetPassword() {
		return tokenResetPassword;
	}

	public void setTokenResetPassword(String tokenResetPassword) {
		this.tokenResetPassword = tokenResetPassword;
	}

	public LocalDate getFechaExpiraToken() {
		return fechaExpiraToken;
	}

	public void setFechaExpiraToken(LocalDate fechaExpiraToken) {
		this.fechaExpiraToken = fechaExpiraToken;
	}

}
