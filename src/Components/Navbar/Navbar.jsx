import './Navbar.css';
import logo_light from '../../assets/logo-black.png';
import logo_dark from '../../assets/logo-white.png';
import search_icon_light from '../../assets/search-b.png';
import search_icon_dark from '../../assets/search-w.png';
import toggle_light from '../../assets/day.png';
import toggle_dark from '../../assets/night.png';
const Navbar = ({theme, setTheme}) => {
	
	const toggle_mode = () => {
		theme == 'light' ? setTheme('dark') : setTheme('light');
	}
	
	return (
		<div className='navbar'>
			<img src={ theme == 'light' ? logo_light : logo_dark} alt="Logo de la empresa" className='logo' />
			<ul>
				<li>Home</li>
				<li>Products</li>
				<li>Features</li>
				<li>About</li>
			</ul>

			<div className='search-box'>
				<input type="text" placeholder='Search' />
				<img src={theme == 'light' ? search_icon_dark : search_icon_light} alt="" />
			</div>

			<img onClick={()=>{toggle_mode()}} src={theme == 'light' ? toggle_dark : toggle_light} alt="" className='toggle-icon' />
		</div>
	)
}

export default Navbar
