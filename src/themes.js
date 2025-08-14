// Gestión de temas de la aplicación
class GestorTemas {
  constructor() {
    this.temaActual = localStorage.getItem('tema') || 'dark';
    this.init();
  }

  init() {
    this.establecerTema(this.temaActual);
    this.configurarBotonToggle();
  }

  establecerTema(tema) {
    document.documentElement.setAttribute('data-theme', tema);
    this.temaActual = tema;
    localStorage.setItem('tema', tema);
    this.actualizarIconoToggle();
    this.actualizarLogo();
    this.actualizarIconoBusqueda();
  }

  actualizarIconoToggle() {
    const iconoToggle = document.getElementById('toggle-theme');
    if (iconoToggle) {
      iconoToggle.src = this.temaActual === 'dark' 
        ? 'src/assets/night.png' 
        : 'src/assets/day.png';
    }
  }

  actualizarLogo() {
    const imagenLogo = document.getElementById('logo-img');
    const logoFooter = document.getElementById('logo-footer-img');
    
    if (imagenLogo) {
      imagenLogo.src = this.temaActual === 'dark' 
        ? 'src/assets/logo-white.png' 
        : 'src/assets/logo-black.png';
    }
    
    if (logoFooter) {
      logoFooter.src = this.temaActual === 'dark' 
        ? 'src/assets/logo-white.png' 
        : 'src/assets/logo-black.png';
    }
  }

  actualizarIconoBusqueda() {
    const iconoBusqueda = document.getElementById('search-icon');
    if (iconoBusqueda) {
      iconoBusqueda.src = this.temaActual === 'dark' 
        ? 'src/assets/search-w.png' 
        : 'src/assets/search-b.png';
    }
  }

  alternarTema() {
    const nuevoTema = this.temaActual === 'dark' ? 'light' : 'dark';
    this.establecerTema(nuevoTema);
  }

  configurarBotonToggle() {
    const botonToggle = document.getElementById('toggle-theme');
    if (botonToggle) {
      botonToggle.addEventListener('click', () => this.alternarTema());
    }
  }
}

// Inicializar el gestor de temas cuando el DOM esté cargado
document.addEventListener('DOMContentLoaded', () => {
  window.gestorTemas = new GestorTemas();
});

// Exportar para uso en otros archivos
window.GestorTemas = GestorTemas;
