package CatsPrograming.ReCore.models.Core;

public class Menu {
	private Integer id;
	private String nombre;
	private boolean activo;
	private int orden;
	private String icon;
	private String color;

	public Menu(Integer id, String nombre, boolean activo, int orden, String icon, String color) {
		this.id = id;
		this.nombre = nombre;
		this.activo = activo;
		this.orden = orden;
		this.icon = icon;
		this.color = color;
	}

	public Menu(String nombre, boolean activo, int orden, String icon, String color) {
		this.nombre = nombre;
		this.activo = activo;
		this.orden = orden;
		this.icon = icon;
		this.color = color;
	}

	public Integer getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean isActivo() {
		return activo;
	}

	public int getOrden() {
		return orden;
	}

	public String getIcon() {
		return icon;
	}

	public String getColor() {
		return color;
	}

}
