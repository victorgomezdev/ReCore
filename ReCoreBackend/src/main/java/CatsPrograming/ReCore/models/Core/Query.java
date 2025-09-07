package CatsPrograming.ReCore.models.Core;

public class Query {
	private Integer id;
	private Menu menu;
	private String table;
	private String queryName;
	private String queryDescription;
	private String fields;
	private boolean canInsert;
	private boolean canEdit;
	private boolean canDelete;
	private boolean debil;
	private String icon;
	private Integer checksum;

	public Query(Integer id, Menu menu, String table, String queryName, String queryDescription, String fields,
			boolean canInsert, boolean canEdit, boolean canDelete, boolean debil, String icon, Integer checksum) {
		this.id = id;
		this.menu = menu;
		this.table = table;
		this.queryName = queryName;
		this.queryDescription = queryDescription;
		this.fields = fields;
		this.canInsert = canInsert;
		this.canEdit = canEdit;
		this.canDelete = canDelete;
		this.debil = debil;
		this.icon = icon;
		this.checksum = checksum;
	}

	public Query(Menu menu, String table, String queryName, String queryDescription, String fields, boolean canInsert,
			boolean canEdit, boolean canDelete, boolean debil, String icon, Integer checksum) {
		this.menu = menu;
		this.table = table;
		this.queryName = queryName;
		this.queryDescription = queryDescription;
		this.fields = fields;
		this.canInsert = canInsert;
		this.canEdit = canEdit;
		this.canDelete = canDelete;
		this.debil = debil;
		this.icon = icon;
		this.checksum = checksum;
	}

	public Query(java.util.Map<String, Object> map) {
		this.id = (Integer) map.get("id");
		this.menu = (Menu) map.get("menu");
		this.table = (String) map.get("table");
		this.queryName = (String) map.get("queryName");
		this.queryDescription = (String) map.get("queryDescription");
		this.fields = (String) map.get("fields");
		this.canInsert = map.get("canInsert") != null && (Boolean) map.get("canInsert");
		this.canEdit = map.get("canEdit") != null && (Boolean) map.get("canEdit");
		this.canDelete = map.get("canDelete") != null && (Boolean) map.get("canDelete");
		this.debil = map.get("debil") != null && (Boolean) map.get("debil");
		this.icon = (String) map.get("icon");
		this.checksum = (Integer) map.get("checksum");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Menu getIdMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public String getQueryDescription() {
		return queryDescription;
	}

	public void setQueryDescription(String queryDescription) {
		this.queryDescription = queryDescription;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public boolean getCanInsert() {
		return canInsert;
	}

	public void setCanInsert(boolean canInsert) {
		this.canInsert = canInsert;
	}

	public boolean getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}

	public boolean getCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	public boolean getDebil() {
		return debil;
	}

	public void setDebil(boolean debil) {
		this.debil = debil;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getChecksum() {
		return checksum;
	}

	public void setChecksum(Integer checksum) {
		this.checksum = checksum;
	}

}
