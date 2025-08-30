package CatsPrograming.ReCore.models.Core;

public class QueryFields {
	private Integer id;
	private Query query;
	private String field;
	private String showName;
	private boolean isRequired;
	private boolean passwordField;
	private boolean colorField;
	private boolean richText;
	private boolean isEditable;
	private boolean visible;
	private boolean ocultarVacio;
	private String grupo;
	private String fieldHelp;
	private Integer ancho;

	public QueryFields(Integer id, Query query, String field, String showName, boolean isRequired,
			boolean passwordField, boolean colorField, boolean richText, boolean isEditable, boolean visible,
			boolean ocultarVacio, String grupo, String fieldHelp, Integer ancho) {
		this.id = id;
		this.query = query;
		this.field = field;
		this.showName = showName;
		this.isRequired = isRequired;
		this.passwordField = passwordField;
		this.colorField = colorField;
		this.richText = richText;
		this.isEditable = isEditable;
		this.visible = visible;
		this.ocultarVacio = ocultarVacio;
		this.grupo = grupo;
		this.fieldHelp = fieldHelp;
		this.ancho = ancho;
	}

	public QueryFields(Query query, String field, String showName, boolean isRequired, boolean passwordField,
			boolean colorField, boolean richText, boolean isEditable, boolean visible, boolean ocultarVacio,
			String grupo, String fieldHelp, Integer ancho) {
		this.query = query;
		this.field = field;
		this.showName = showName;
		this.isRequired = isRequired;
		this.passwordField = passwordField;
		this.colorField = colorField;
		this.richText = richText;
		this.isEditable = isEditable;
		this.visible = visible;
		this.ocultarVacio = ocultarVacio;
		this.grupo = grupo;
		this.fieldHelp = fieldHelp;
		this.ancho = ancho;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public boolean isPasswordField() {
		return passwordField;
	}

	public void setPasswordField(boolean passwordField) {
		this.passwordField = passwordField;
	}

	public boolean isColorField() {
		return colorField;
	}

	public void setColorField(boolean colorField) {
		this.colorField = colorField;
	}

	public boolean isRichText() {
		return richText;
	}

	public void setRichText(boolean richText) {
		this.richText = richText;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isOcultarVacio() {
		return ocultarVacio;
	}

	public void setOcultarVacio(boolean ocultarVacio) {
		this.ocultarVacio = ocultarVacio;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getFieldHelp() {
		return fieldHelp;
	}

	public void setFieldHelp(String fieldHelp) {
		this.fieldHelp = fieldHelp;
	}

	public Integer getAncho() {
		return ancho;
	}

	public void setAncho(Integer ancho) {
		this.ancho = ancho;
	}

}
