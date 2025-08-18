import './Register.css';
import { useNavigate } from 'react-router-dom';
import Footer from "../../Components/footer/Footer";
import Navbar from "../../Components/Navbar/Navbar";
import InputForm from "../../Components/Inputs/InputText/InputText";
import InputButton from "../../Components/Inputs/InputButton/InputButton";
import { IoLogIn } from "react-icons/io5";
import '../../utils/FormMiddleware.js';

const RegisterPage = () => {
	const navigate = useNavigate();
	
	const handleRegister = async (e) => {
		e.preventDefault();
		
		const formData = new FormData(e.target);
		const registerData = Object.fromEntries(formData);
		
		const result = await window.FormMiddleware.submit('/api/auth/register', registerData);
		
		if(result.success){
			alert('Registro exitoso!');
			localStorage.setItem('token', result.data.token);
			navigate('/login');
		}
	}
	return <>
		<Navbar />
		<div className="container">
			<div className="container-body">
				<div className="register-background" />
				<div className="register-page">
					<form className="register-box" onSubmit={handleRegister}>
						<span className='register-box-titulo'>Registrarse</span>
						<span className='register-box-subtitulo'>Por favor ingrese sus datos</span>
						
						<InputForm label={"Nombre"} placeholder={"EJ: Pepito"} name="name" type="text" />
						
						<InputForm label={"Email"} placeholder={"EJ: pepito@sanchez.com"} name="email" type="email" />

						<InputForm label={"Contraseña"} placeholder={"********"} name="password" type="password" />
						
						<InputButton width="auto" text="Registrarse" icon={<IoLogIn />} type="submit"></InputButton>
					</form>
				</div>
			</div>
		</div>
		<Footer />
	</>
}

export default RegisterPage;