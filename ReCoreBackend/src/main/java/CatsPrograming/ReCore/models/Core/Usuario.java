package CatsPrograming.ReCore.models.Core;

import java.time.LocalDate;

import CatsPrograming.ReCore.models.Personas.Persona;

public class Usuario {
	private Integer id;
	private Integer idpersona;
	private String email;
	private String passwordHash;
	private boolean activo;
	private LocalDate fechaRegistro;
	private LocalDate fechaUltimoLogin;
	private Integer intentosLogin;
	private LocalDate bloqueadoHasta;
	private String tokenVerificacion;
	private String tokenResetPassword;
	private LocalDate fechaExpiraToken;

	public Usuario(Integer id, Integer idpersona, String email, String passwordHash, boolean activo,
			LocalDate fechaRegistro, LocalDate fechaUltimoLogin, Integer intentosLogin, LocalDate bloqueadoHasta,
			String tokenVerificacion, String tokenResetPassword, LocalDate fechaExpiraToken) {
		this.id = id;
		this.idpersona = idpersona;
		this.email = email;
		this.passwordHash = passwordHash;
		this.activo = activo;
		this.fechaRegistro = fechaRegistro;
		this.fechaUltimoLogin = fechaUltimoLogin;
		this.intentosLogin = intentosLogin;
		this.bloqueadoHasta = bloqueadoHasta;
		this.tokenVerificacion = tokenVerificacion;
		this.tokenResetPassword = tokenResetPassword;
		this.fechaExpiraToken = fechaExpiraToken;
	}

	public Usuario(Integer idpersona, String email, String passwordHash, boolean activo, LocalDate fechaRegistro,
			LocalDate fechaUltimoLogin, Integer intentosLogin, LocalDate bloqueadoHasta, String tokenVerificacion,
			String tokenResetPassword, LocalDate fechaExpiraToken) {
		this.idpersona = idpersona;
		this.email = email;
		this.passwordHash = passwordHash;
		this.activo = activo;
		this.fechaRegistro = fechaRegistro;
		this.fechaUltimoLogin = fechaUltimoLogin;
		this.intentosLogin = intentosLogin;
		this.bloqueadoHasta = bloqueadoHasta;
		this.tokenVerificacion = tokenVerificacion;
		this.tokenResetPassword = tokenResetPassword;
		this.fechaExpiraToken = fechaExpiraToken;
	}

	public Usuario(java.util.Map<String, Object> map) {
		this.id = (Integer) map.get("id");
		this.idpersona = (Integer) map.get("idpersona");
		this.email = (String) map.get("email");
		this.passwordHash = (String) map.get("passwordHash");
		this.activo = map.get("activo") != null && (Boolean) map.get("activo");
		this.fechaRegistro = (LocalDate) map.get("fechaRegistro");
		this.fechaUltimoLogin = (LocalDate) map.get("fechaUltimoLogin");
		this.intentosLogin = (Integer) map.get("intentosLogin");
		this.bloqueadoHasta = (LocalDate) map.get("bloqueadoHasta");
		this.tokenVerificacion = (String) map.get("tokenVerificacion");
		this.tokenResetPassword = (String) map.get("tokenResetPassword");
		this.fechaExpiraToken = (LocalDate) map.get("fechaExpiraToken");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdpersona() {
		return idpersona;
	}

	public void setIdpersona(Integer idpersona) {
		this.idpersona = idpersona;
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

	public LocalDate getFechaUltimoLogin() {
		return fechaUltimoLogin;
	}

	public void setFechaUltimoLogin(LocalDate fechaUltimoLogin) {
		this.fechaUltimoLogin = fechaUltimoLogin;
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
