import "./MenuLateral.css";
import logo_light from "../../assets/logo-black.png";
import logo_dark from "../../assets/logo-white.png";
import { useState, useContext, useEffect } from "react";
import { ThemeContext } from "../../ThemeContext.jsx";
import toggle_light from "../../assets/day.png";
import toggle_dark from "../../assets/night.png";
import InputText from "../Inputs/InputText/InputText.jsx";
import PropTypes from "prop-types";

const MenuLateral = ({ onSeleccionTabla }) => {
  // Menú dinámico desde backend
  const [menuItems, setMenuItems] = useState([]);
  const [expandedMenus, setExpandedMenus] = useState(new Set());

  useEffect(() => {
    fetch("/api/core/getMenu")
      .then((res) => {
        if (!res.ok) {
          throw new Error(`HTTP error! status: ${res.status}`);
        }
        return res.json();
      })
      .then((data) => {
        console.log("Respuesta completa de getMenu:", data);

        if (data.success && Array.isArray(data.data)) {
          setMenuItems(data.data);
          console.log(
            "Menú cargado exitosamente:",
            data.data.length,
            "elementos"
          );
        } else {
          console.log("Estructura de datos no esperada:", data);
          setMenuItems([]);
        }
      })
      .catch((error) => {
        console.error("Error al cargar el menú:", error);
        setMenuItems([]);
      });
  }, []);

  // Theme
  const { theme, setTheme } = useContext(ThemeContext);

  const handleThemeChange = () => {
    theme == "light" ? setTheme("dark") : setTheme("light");
  };

  // Manejar expansión/colapso de menús
  const toggleMenu = (menuId) => {
    const newExpandedMenus = new Set(expandedMenus);
    if (newExpandedMenus.has(menuId)) {
      newExpandedMenus.delete(menuId);
    } else {
      newExpandedMenus.add(menuId);
    }
    setExpandedMenus(newExpandedMenus);
  };

  return (
    <div className="menu-lateral">
      <div>
        <img
          src={theme == "light" ? logo_light : logo_dark}
          alt="Logo de la empresa"
          className="logo"
        />
      </div>
      <div className="menu-lateral-tools">
        <InputText placeholder={"Buscar"}></InputText>
        <div className="toggle-container">
          <img
            onClick={() => {
              handleThemeChange();
            }}
            src={theme == "light" ? toggle_dark : toggle_light}
            alt=""
            className="toggle-icon"
          />
        </div>
      </div>

      <ul className="menu-lista">
        {menuItems.map((menu) => (
          <li key={menu.ID} className="menu-item">
            <div className="menu-header" onClick={() => toggleMenu(menu.ID)}>
              <span className="menu-icon">{menu.ICON}</span>
              <span className="menu-nombre">{menu.NOMBRE}</span>
              {Array.isArray(menu.tablas) && menu.tablas.length > 0 && (
                <span className="menu-arrow">
                  {expandedMenus.has(menu.ID) ? "▼" : "▶"}
                </span>
              )}
            </div>
            {Array.isArray(menu.tablas) &&
              menu.tablas.length > 0 &&
              expandedMenus.has(menu.ID) && (
                <ul className="submenu">
                  {menu.tablas.map((tabla) => (
                    <li
                      key={tabla.ID}
                      className="submenu-item"
                      onClick={() => onSeleccionTabla(tabla.TABLE_NAME)}
                    >
                      <span className="submenu-icon">{tabla.ICON}</span>
                      <span className="submenu-nombre">{tabla.QUERY_NAME}</span>
                    </li>
                  ))}
                </ul>
              )}
          </li>
        ))}
      </ul>
    </div>
  );
};

MenuLateral.propTypes = {
  onSeleccionTabla: PropTypes.func.isRequired,
};

export default MenuLateral;
