import './InputButton.css'

const Boton = ({ text = "Botón", onClick, width = 'auto', icon }) => {
  return (
    <button style={{ width }} className="button-25" role="button" onClick={onClick}>
      <div className="button-25-contenido">
        {text}
        {icon}
      </div>
    </button>
  );
};

export default Boton;