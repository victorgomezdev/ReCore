import './Navbar.css';
import logo_light from '../../assets/logo-black.png';
import logo_dark from '../../assets/logo-white.png';
import Boton from '../Inputs/InputButton/InputButton.jsx'
import toggle_light from '../../assets/day.png';
import toggle_dark from '../../assets/night.png';
import { TiThMenu } from "react-icons/ti";
import { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { ThemeContext } from "../../ThemeContext.jsx";

const Navbar = () => {

	// Menu
	const [menu_open, setMenu_open] = useState(false);
	const navigate = useNavigate();

	const toggle_menu = () => {
		menu_open ? setMenu_open(false) : setMenu_open(true);
	}

	// Theme
	const { theme, setTheme } = useContext(ThemeContext);

	//Separar en un componente todo lo relacionado con el botón theme
	const handleThemeChange = (newTheme) => {
		theme == 'light' ? setTheme('dark') : setTheme('light');
	};

	// Navegación
	const handleLoginClick = () => {
		navigate('/login');
	}

	const handleRegisterClick = () => {
		// Preparamos para cuando tengamos la ruta de registro
		navigate('/register');
	}

	return (
		<div className='navbar'>
			<div className='contenedor-logo'>
				<img src={theme == 'light' ? logo_light : logo_dark} alt="Logo de la empresa" className='logo' />
				<TiThMenu className='menu-burger' onClick={() => { toggle_menu() }} />
			</div>

			<div className={`contenedor-botones ${menu_open ? 'active' : 'inactive'}`}>
				<ul>
					<li>Inicio</li>
					<li>Productos</li>
					<li>¿Quienes somos?</li>
				</ul>

				{/* <div className='search-box'>
					<input type="text" placeholder='Buscar' />
				</div> */}
				
				<Boton text="Crear cuenta" onClick={handleRegisterClick}></Boton>
				<Boton text="Iniciar sesión" onClick={handleLoginClick}></Boton>


				<img onClick={() => { handleThemeChange() }} src={theme == 'light' ? toggle_dark : toggle_light} alt="" className='toggle-icon' />
			</div>
		</div>
	)
}

export default Navbar
