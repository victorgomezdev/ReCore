import './Navbar.css';
import logo_light from '../../assets/logo-black.png';
import logo_dark from '../../assets/logo-white.png';
import Boton from '../Inputs/InputButton/InputButton.jsx'
import toggle_light from '../../assets/day.png';
import toggle_dark from '../../assets/night.png';
import { TiThMenu } from "react-icons/ti";
import { useState } from 'react';
import { useContext } from 'react';
import { ThemeContext } from "../../ThemeContext.jsx";

const Navbar = () => {

	// Menu
	const [menu_open, setMenu_open] = useState(false);

	const toggle_menu = () => {
		menu_open ? setMenu_open(false) : setMenu_open(true);
	}

	// Theme
	const { theme, setTheme } = useContext(ThemeContext);

	//TODO: Hacer que el elegir sea entre diferentes modos
	//Separar en un componente todo lo relacionado con el botón theme
	const handleThemeChange = (newTheme) => {
		theme == 'light' ? setTheme('dark') : setTheme('light');
	};

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
				
				<Boton text="Crear cuenta"></Boton>
				<Boton text="Iniciar sesión"></Boton>


				<img onClick={() => { handleThemeChange() }} src={theme == 'light' ? toggle_dark : toggle_light} alt="" className='toggle-icon' />
			</div>
		</div>
	)
}

export default Navbar
