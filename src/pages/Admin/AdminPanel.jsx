import "./AdminPanel.css";
import { useState, useContext, useEffect } from "react";
import { ThemeContext } from "../../ThemeContext.jsx";
import MenuLateral from "../../Components/MenuLateral/MenuLateral.jsx";
import GridDatos from "../../Components/GridDatos/GridDatos.jsx";

const AdminPanel = () => {
  // Theme
  const { theme, setTheme } = useContext(ThemeContext);
  const [tablaSeleccionada, setTablaSeleccionada] = useState(null);
  // Esta función se pasa al menú lateral
  const onSeleccionTabla = (nombreTabla) => {
    setTablaSeleccionada(nombreTabla);
  };

  return (
    <div className="admin-panel">
      <MenuLateral onSeleccionTabla={onSeleccionTabla} />
      <div className="contenido">
        {tablaSeleccionada ? (
          <GridDatos nombreTabla={tablaSeleccionada} />
        ) : (
          <div>Selecciona una tabla del menú lateral.</div>
        )}
      </div>
    </div>
  );
};

export default AdminPanel;
