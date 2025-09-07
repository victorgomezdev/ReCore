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

	public QueryFields(java.util.Map<String, Object> map, Query query) {
		this.id = map.get("id") != null ? ((Number) map.get("id")).intValue() : null;
		this.query = query;
		this.field = (String) map.get("field");
		this.showName = (String) map.get("show_name");
		this.isRequired = map.get("is_required") != null && ((Number) map.get("is_required")).intValue() == 1;
		this.passwordField = map.get("password_field") != null && ((Number) map.get("password_field")).intValue() == 1;
		this.colorField = map.get("color_field") != null && ((Number) map.get("color_field")).intValue() == 1;
		this.richText = map.get("rich_text") != null && ((Number) map.get("rich_text")).intValue() == 1;
		this.isEditable = map.get("is_editable") != null && ((Number) map.get("is_editable")).intValue() == 1;
		this.visible = map.get("visible") != null && ((Number) map.get("visible")).intValue() == 1;
		this.ocultarVacio = map.get("ocultar_vacio") != null && ((Number) map.get("ocultar_vacio")).intValue() == 1;
		this.grupo = (String) map.get("grupo");
		this.fieldHelp = (String) map.get("field_help");
		this.ancho = map.get("ancho") != null ? ((Number) map.get("ancho")).intValue() : null;
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

	public boolean getRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public boolean getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(boolean passwordField) {
		this.passwordField = passwordField;
	}

	public boolean getColorField() {
		return colorField;
	}

	public void setColorField(boolean colorField) {
		this.colorField = colorField;
	}

	public boolean getRichText() {
		return richText;
	}

	public void setRichText(boolean richText) {
		this.richText = richText;
	}

	public boolean getIsEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean getOcultarVacio() {
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
