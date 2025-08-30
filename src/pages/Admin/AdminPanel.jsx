import './AdminPanel.css'
import logo_light from '../../assets/logo-black.png';
import logo_dark from '../../assets/logo-white.png';
import { useState, useContext, useEffect } from 'react';
import { ThemeContext } from "../../ThemeContext.jsx";
import toggle_light from '../../assets/day.png';
import toggle_dark from '../../assets/night.png';
import InputTextFlat from "../../Components/Inputs/InputText/InputTextFlat.jsx";



const AdminPanel = () => {

	//TODO: Empezar a traer de querys.
	//TODO: Implementar función para crear menu si no existe, al agregar tabla a menu.

		// Menú dinámico desde backend
		const [menuItems, setMenuItems] = useState([]);
		useEffect(() => {
			fetch('/api/core/getMenu')
				.then(res => res.json())
				.then(data => {
					if (data.success && Array.isArray(data.data)) {
						setMenuItems(data.data);
					} else {
						setMenuItems([]);
					}
				})
				.catch(() => setMenuItems([]));
		}, []);

	// Theme
	const { theme, setTheme } = useContext(ThemeContext);

	//Separar en un componente todo lo relacionado con el botón theme
	const handleThemeChange = (newTheme) => {
		theme == 'light' ? setTheme('dark') : setTheme('light');
	};


	return (
		<>
			<div className="admin-panel">
				<div className="menu-lateral">
					<div>
						<img src={theme == 'light' ? logo_light : logo_dark} alt="Logo de la empresa" className='logo' />
					</div>
					<div className='menu-lateral-tools'>
						<InputTextFlat placeholder={"Buscar"}></InputTextFlat>
						<div className='toggle-container'>
							<img onClick={() => { handleThemeChange() }} src={theme == 'light' ? toggle_dark : toggle_light} alt="" className='toggle-icon' />
						</div>
					</div>

					<ul>
						{menuItems.map(menu => (
							<li key={menu.id}>
								<span>{menu.icon}</span> {menu.nombre}
								{Array.isArray(menu.tablas) && menu.tablas.length > 0 && (
									<ul>
										{menu.tablas.map(tabla => (
											<li key={tabla.id}>
												<span>{tabla.icon}</span> {tabla.query_name}
											</li>
										))}
									</ul>
								)}
							</li>
						))}
					</ul>
				</div>
				<div className="contenido">
					Lorem ipsum dolor sit amet consectetur, adipisicing elit. Eum minima porro delectus nemo amet. Voluptas soluta fuga molestias animi velit ad, eos exercitationem amet cumque maiores iusto, dolorum laborum a?
					Lorem ipsum dolor sit, amet consectetur adipisicing elit. Facilis, debitis! Porro explicabo minima numquam vitae dolor tempora culpa tenetur nisi modi iste ipsam, assumenda sapiente labore illum sint aperiam iure.
				</div>
			</div>
		</>
	)
}

export default AdminPanel;