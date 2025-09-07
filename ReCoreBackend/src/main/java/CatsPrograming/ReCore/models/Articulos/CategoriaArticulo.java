package CatsPrograming.ReCore.models.Articulos;

public class CategoriaArticulo {
	private Integer id;
	private Integer idcategoria;
	private Integer idarticulo;
	private Integer activo;

	// Constructor completo
	public CategoriaArticulo(Integer id, Integer idcategoria, Integer idarticulo, Integer activo) {
		this.id = id;
		this.idcategoria = idcategoria;
		this.idarticulo = idarticulo;
		this.activo = activo;
	}

	// Constructor sin ID (para inserts)
	public CategoriaArticulo(Integer idcategoria, Integer idarticulo, Integer activo) {
		this.idcategoria = idcategoria;
		this.idarticulo = idarticulo;
		this.activo = activo;
	}

	// Constructor desde Map (para queries)
	public CategoriaArticulo(java.util.Map<String, Object> map) {
		this.id = map.get("id") != null ? ((Number) map.get("id")).intValue() : null;
		this.idcategoria = map.get("idcategoria") != null ? ((Number) map.get("idcategoria")).intValue() : null;
		this.idarticulo = map.get("idarticulo") != null ? ((Number) map.get("idarticulo")).intValue() : null;
		this.activo = map.get("activo") != null ? ((Number) map.get("activo")).intValue() : null;
	}

	// Getters y Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdcategoria() {
		return idcategoria;
	}

	public void setIdcategoria(Integer idcategoria) {
		this.idcategoria = idcategoria;
	}

	public Integer getIdarticulo() {
		return idarticulo;
	}

	public void setIdarticulo(Integer idarticulo) {
		this.idarticulo = idarticulo;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}
}
