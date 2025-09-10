import "./GridDatos.css";
import { useEffect, useState } from "react";

const GridDatos = ({ nombreTabla }) => {
  const [datos, setDatos] = useState([]);
  const [campos, setCampos] = useState([]);
  const [fieldsInfo, setFieldsInfo] = useState({});

  useEffect(() => {
    if (!nombreTabla) return;
    fetch(`/api/core/getTabla?tabla=${nombreTabla}`)
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
        setDatos(data.data || []);
        setCampos(data.columns || []);
        setFieldsInfo(data.queryFields || []);
      });
  }, [nombreTabla]);

  if (!nombreTabla) return null;
  if (datos.length === 0) return <div>No hay datos para {nombreTabla}</div>;

  function Capitalize(str) {
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
  }

  return (
    <>
      <div className="grid-datos">
        <div className="grid-datos-header">
          {campos.map((campo) => {
            const infoField = fieldsInfo.find((f) => f.FIELD === campo);
            console.log(infoField);

            if (infoField.VISIBLE === 1) {
              console.log(infoField.TIPO);

              return (
                <div key={campo} className={`grid-datos-header-item`}>
                  {Capitalize(infoField.SHOW_NAME)}
                </div>
              );
            }
            return null;
          })}
        </div>
        <div className="grid-datos-body">
          {datos.map((fila, idx) => (
            <div key={idx} className="grid-datos-body-row">
              {campos.map((campo) => {
                const infoField = fieldsInfo.find((f) => f.FIELD === campo);

                let value = fila[campo];
                if (infoField.TIPO === "TIMESTAMP" && value) {
                  value = new Date(value).toLocaleString();
                  const fecha = new Date(value);
                  const pad = (n) => n.toString().padStart(2, "0");
                  value = `${pad(fecha.getDate())}/${pad(
                    fecha.getMonth() + 1
                  )}/${fecha.getFullYear()} ${pad(fecha.getHours())}:${pad(
                    fecha.getMinutes()
                  )}`;
                }
                return (
                  <div
                    key={campo}
                    className={`grid-datos-body-item ${infoField.TIPO}`}
                  >
                    {value}
                  </div>
                );
              })}
            </div>
          ))}
        </div>
      </div>
    </>
  );
};

export default GridDatos;
