import './InputButton.css'

const Boton = ({ text = "Botón", onClick }) => {
  return (
    <button className="button-25" role="button" onClick={onClick}>
      {text}
    </button>
  );
};

export default Boton;