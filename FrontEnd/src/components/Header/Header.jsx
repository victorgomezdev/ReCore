import { useState } from "react";
import Logo from "../../assets/logo.png";
import "./Hader.css";

const Header = () => {
  const [menuOpen, setMenuOpen] = useState(false);

  const toggleMenu = () => {
    setMenuOpen(!menuOpen);
  };

  return (
    <header className="header-page">
      <div className="header-container">
        <div className="logo">
          <img src={Logo} alt="Logo" />
        </div>

        <nav className={`nav-menu ${menuOpen ? "open" : ""}`}>
          <ul>
            <li>
              <a href="/">Inicio</a>
            </li>
            <li>
              <a href="/productos">Productos</a>
            </li>
            <li>
              <a href="/login">Iniciar Sesión</a>
            </li>
            <li>
              <a href="/registro">Crear Cuenta</a>
            </li>
          </ul>
        </nav>

        <div className="menu-container">
          <button className="hamburger-btn" onClick={toggleMenu}>
            <span className="hamburger-line"></span>
            <span className="hamburger-line"></span>
            <span className="hamburger-line"></span>
          </button>
        </div>
      </div>
    </header>
  );
};

export default Header;
