package CatsPrograming.ReCore.services.Core;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CatsPrograming.ReCore.dao.Core.MenuDao;
import CatsPrograming.ReCore.models.Core.Menu;

@Service
public class MenuService {

	@Autowired
	private MenuDao menuDao;

	public void insertMenu(Menu menu) {
		menuDao.insert(menu);
	}

	public void updateMenu(Menu menu) {
		menuDao.update(menu);
	}

	public void deleteMenu(int id) {
		menuDao.delete(id);
	}

	public Menu getMenuById(int id) {
		return menuDao.getById(id);
	}

	public List<Menu> getAllMenus() {
		return menuDao.getAll();
	}

	public List<Menu> getMenuesWhere(String where) {
		return menuDao.getWhere(where);
	}

	public List<Map<String, Object>> obtenerMenu() {
		return menuDao.obtenerMenu();
	}

	public Menu getMenuByName(String name) {
		List<Menu> menus = menuDao.getWhere("nombre = '" + name + "'");
		if (menus.isEmpty()) {
			// Si no existe, lo crea
			Menu nuevoMenu = new Menu(name, true, 1, "folder", "blue");
			menuDao.insert(nuevoMenu);
			menus = menuDao.getWhere("nombre = '" + name + "'");
		}
		return menus.get(0);
	}
}
