import './InputButtonSecundario.css'

const InputButtonSecundario = ({ text = "Botón", onClick, button, width, float }) => {
  return (
    <button style={{width, float}} className="button-25-secundario" role="button" onClick={onClick}>
      <div className='button-25-secundario-contenido'>
        {text}
        {button}
      </div>
    </button>
  );
};

export default InputButtonSecundario;