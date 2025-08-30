
package CatsPrograming.ReCore.models.Personas;

import java.time.LocalDate;
import java.util.List;

public class Persona {
    private Integer id;
    private String nombre;
    private String apellido;
    private String dni;
    private String cuit;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private LocalDate fechaRegistro;
    private String notas;
    private List<Direccion> direcciones;

    public Persona(Integer id, String nombre, String apellido, String dni, String cuit, String email, String telefono,
            LocalDate fechaNacimiento, LocalDate fechaRegistro, String notas, List<Direccion> direcciones) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.cuit = cuit;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
        this.notas = notas;
        this.direcciones = direcciones;
    }

    public Persona(String nombre, String apellido, String dni, String cuit, String email, String telefono,
            LocalDate fechaNacimiento, LocalDate fechaRegistro, String notas, List<Direccion> direcciones) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.cuit = cuit;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
        this.notas = notas;
        this.direcciones = direcciones;
    }

    /**
     * Constructor que recibe un Map y una lista de direcciones (puede ser vacias)
     */
    public Persona(java.util.Map<String, Object> map, List<Direccion> direcciones) {
        this.id = map.get("id") != null ? ((Number) map.get("id")).intValue() : null;
        this.nombre = (String) map.get("nombre");
        this.apellido = (String) map.get("apellido");
        this.dni = (String) map.get("dni");
        this.cuit = (String) map.get("cuit");
        this.email = (String) map.get("email");
        this.telefono = (String) map.get("telefono");
        Object fechaNac = map.get("fecha_nacimiento");
        if (fechaNac instanceof java.sql.Date) {
            this.fechaNacimiento = ((java.sql.Date) fechaNac).toLocalDate();
        } else if (fechaNac instanceof java.time.LocalDate) {
            this.fechaNacimiento = (java.time.LocalDate) fechaNac;
        }
        Object fechaReg = map.get("fecha_registro");
        if (fechaReg instanceof java.sql.Date) {
            this.fechaRegistro = ((java.sql.Date) fechaReg).toLocalDate();
        } else if (fechaReg instanceof java.time.LocalDate) {
            this.fechaRegistro = (java.time.LocalDate) fechaReg;
        }
        this.direcciones = direcciones;
        this.notas = (String) map.get("notas");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}
