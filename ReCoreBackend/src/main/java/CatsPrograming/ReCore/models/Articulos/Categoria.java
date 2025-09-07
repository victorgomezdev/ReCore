package CatsPrograming.ReCore.models.Articulos;

public class Categoria {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer activo;

    // Constructor completo
    public Categoria(Integer id, String nombre, String descripcion, Integer activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    // Constructor sin ID (para inserts)
    public Categoria(String nombre, String descripcion, Integer activo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    // Constructor desde Map (para queries)
    public Categoria(java.util.Map<String, Object> map) {
        this.id = map.get("id") != null ? ((Number) map.get("id")).intValue() : null;
        this.nombre = (String) map.get("nombre");
        this.descripcion = (String) map.get("descripcion");
        this.activo = map.get("activo") != null ? ((Number) map.get("activo")).intValue() : null;
    }

    // Getters y Setters
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }
}
