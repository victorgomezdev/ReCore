import './Login.css'
import '../../themes.css';
import Navbar from '../../Components/Navbar/Navbar.jsx';
import InputForm from "../../Components/Inputs/InputText/InputText.jsx";
import InputButton from '../../Components/Inputs/InputButton/InputButton.jsx'
import InputButtonSecundario from '../../Components/Inputs/InputButton/InputButtonSecundario.jsx'
import { IoLogIn } from "react-icons/io5";
import Footer from '../../Components/footer/Footer.jsx'
import '../../utils/FormMiddleware.js';
const LoginPage = () => {
	
	const handleLogin = async (e) => {
		e.preventDefault();
		
		const formData = new FormData(e.target);
		const loginData = Object.fromEntries(formData);
		
		const result = await window.FormMiddleware.submit('/api/auth/login', loginData);
		
		if (result.success) {
			alert('Login exitoso! Persona ID: ' + result.data.personaId);
			localStorage.setItem('token', result.data.token);
			window.location.href = '/dashboard.html';
		}
	};
	
	return (
		<>
			<Navbar />
			<div className='container'>
				<div className="container-body">
					<div className="login-background" />
					<div className="login-page">
						<form className="login-box" onSubmit={handleLogin}>
							<span className='login-box-titulo'>Bienvenido!</span>
							<span>Por favor ingrese sus datos</span>
							
							<InputForm label={"Email"} placeholder={"EJ: pepito@sanchez.com"} name="email" type="email" />
							
							<InputForm label={"Contraseña"} placeholder={"********"} name="password" type="password" />
							
							<InputButton width="auto" text="Iniciar sesión" icon={<IoLogIn />} type="submit"></InputButton>
							<InputButtonSecundario width="auto" text="Registrarse"></InputButtonSecundario>
						</form>
					</div>
				</div>
			</div>
			<Footer />
		</>
	);
};

export default LoginPage;
