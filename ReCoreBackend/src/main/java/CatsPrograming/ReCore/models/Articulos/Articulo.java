package CatsPrograming.ReCore.models.Articulos;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public class Articulo {
	private Integer id;
	private String nombre;
	private String descripcion;
	private BigDecimal precio;
	private Integer activo;
	private List<Categoria> categorias; // Lista de categorías asociadas

	// Constructor completo
	public Articulo(Integer id, String nombre, String descripcion, BigDecimal precio,
			Integer activo, List<Categoria> categorias) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.activo = activo;
		this.categorias = categorias != null ? categorias : new ArrayList<>();
	}

	// Constructor sin ID (para inserts)
	public Articulo(String nombre, String descripcion, BigDecimal precio, Integer activo) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.activo = activo;
		this.categorias = new ArrayList<>();
	}

	// Constructor desde Map (para queries) - NO carga categorías desde map
	public Articulo(java.util.Map<String, Object> map) {
		this.id = map.get("id") != null ? ((Number) map.get("id")).intValue() : null;
		this.nombre = (String) map.get("nombre");
		this.descripcion = (String) map.get("descripcion");
		this.precio = map.get("precio") != null ? (BigDecimal) map.get("precio") : null;
		this.activo = map.get("activo") != null ? ((Number) map.get("activo")).intValue() : null;
		// categorias se cargan por separado en el DAO
		this.categorias = new ArrayList<>();
	}

	// Constructor con categorias desde Map
	public Articulo(java.util.Map<String, Object> map, List<Categoria> categorias) {
		this(map);
		this.categorias = categorias != null ? categorias : new ArrayList<>();
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

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias != null ? categorias : new ArrayList<>();
	}

	// Métodos de utilidad para manejar categorías
	public void agregarCategoria(Categoria categoria) {
		if (categoria != null && !categorias.contains(categoria)) {
			categorias.add(categoria);
		}
	}

	public void removerCategoria(Categoria categoria) {
		categorias.remove(categoria);
	}

	public void removerCategoriaPorId(Integer idCategoria) {
		categorias.removeIf(cat -> cat.getId().equals(idCategoria));
	}

	public boolean tieneCategoria(Integer idCategoria) {
		return categorias.stream().anyMatch(cat -> cat.getId().equals(idCategoria));
	}
}
