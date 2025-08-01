import './Login.css'
import '../../themes.css';
import { ThemeProvider } from '../../ThemeContext.jsx';
import Navbar from '../../Components/Navbar/Navbar.jsx';
import InputForm from "../../Components/Inputs/InputText/InputText.jsx";
import InputButton from '../../Components/Inputs/InputButton/InputButton.jsx'
import InputButtonSecundario from '../../Components/Inputs/InputButton/InputButtonSecundario.jsx'
import { IoLogIn } from "react-icons/io5";
import Footer from '../../Components/footer/Footer.jsx'
const LoginPage = () => {
	return (
		<ThemeProvider>
			<Navbar />
			<div className='container'>
				<div className="container-body">
					<div className="login-background" />
					<div className="login-page">
						<div className="login-box">
							<span className='login-box-titulo'>Bienvenido!</span>
							<span>Por favor ingrese sus datos</span>
							<InputForm label={"Usuario"} placeholder={"EJ: Pepito"} />
							{/* Perdón por el placeholder xd */}
							<InputForm label={"Contraseña"} placeholder={"EJ: ********"} />
							<InputButton width="auto" text="Iniciar sesión" icon={<IoLogIn />}></InputButton>
							<InputButtonSecundario width="auto" text="Registrarse"></InputButtonSecundario>
						</div>
					</div>
				</div>
			</div>
			<Footer />
		</ThemeProvider>
	);
};

export default LoginPage;
