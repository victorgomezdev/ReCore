import logo_light from '../../assets/logo-black.png';
import logo_dark from '../../assets/logo-white.png';
import './Footer.css';
import { useContext } from 'react';
import { ThemeContext } from "../../ThemeContext.jsx";
import { FaInstagram } from "react-icons/fa";
import { FiFacebook } from "react-icons/fi";
import { FaXTwitter } from "react-icons/fa6";


const Footer = () => {
	const { theme, setTheme } = useContext(ThemeContext);
	return (<>
		<div className="footer">
			<div className="logo-footer">
				<img src={theme == 'light' ? logo_light : logo_dark} alt="Logo de la empresa" className='logo' />
				<span>Todos los derechos reservados</span>
			</div>
			<div className='redes-footer'>
				<div className='red-footer sombreado'>
					<FaInstagram />
				</div>
				<div className='red-footer sombreado'>
					<FiFacebook />
				</div>
				<div className='red-footer sombreado'>
					<FaXTwitter />
				</div>
			</div>
		</div>
	</>);
}
export default Footer;