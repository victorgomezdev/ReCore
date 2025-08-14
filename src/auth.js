// Gestión de autenticación
class GestorAutenticacion {
  constructor() {
    this.urlBase = 'http://localhost:8080/api/auth';
    this.init();
  }

  init() {
    this.actualizarNavbar();
    this.verificarAutenticacionEnPaginasProtegidas();
  }

  // Verificar si el usuario está logueado
  estaLogueado() {
    const token = localStorage.getItem('token');
    const usuario = localStorage.getItem('user');
    return !!(token && usuario);
  }

  // Obtener datos del usuario actual
  obtenerUsuarioActual() {
    const datosUsuario = localStorage.getItem('user');
    return datosUsuario ? JSON.parse(datosUsuario) : null;
  }

    // Actualizar navbar según el estado de autenticación (tu estructura original)
  actualizarNavbar() {
    const authButtons = document.getElementById('auth-buttons');
    const userInfo = document.getElementById('user-info');
    const dashboardLink = document.getElementById('dashboard-link');
    const userNameDisplay = document.getElementById('user-name-display');

    if (this.estaLogueado()) {
      const usuario = this.obtenerUsuarioActual();
      if (authButtons) authButtons.style.display = 'none';
      if (userInfo) userInfo.style.display = 'block';
      if (dashboardLink) dashboardLink.style.display = 'block';
      if (userNameDisplay) {
        userNameDisplay.textContent = `${usuario.firstName || usuario.username}`;
        if (usuario.isRoot) {
          userNameDisplay.innerHTML += ' <span class="root-badge-navbar">ROOT</span>';
        }
      }
    } else {
      if (authButtons) authButtons.style.display = 'block';
      if (userInfo) userInfo.style.display = 'none';
      if (dashboardLink) dashboardLink.style.display = 'none';
    }
  }

  // Verificar si la página actual requiere autenticación
  verificarAutenticacionEnPaginasProtegidas() {
    const paginasProtegidas = ['dashboard.html'];
    const paginaActual = window.location.pathname.split('/').pop();
    
    if (paginasProtegidas.includes(paginaActual) && !this.estaLogueado()) {
      this.redirigirA('login.html');
    }

    // Redirigir si ya está logueado en páginas de auth
    const paginasAuth = ['login.html', 'register.html'];
    if (paginasAuth.includes(paginaActual) && this.estaLogueado()) {
      this.redirigirA('dashboard.html');
    }
  }

  // Método de inicio de sesión
  async iniciarSesion(usuarioOEmail, contrasena) {
    try {
      const respuesta = await fetch(`${this.urlBase}/signin`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          usernameOrEmail: usuarioOEmail,
          password: contrasena
        }),
      });

      if (respuesta.ok) {
        const datos = await respuesta.json();
        
        // Guardar token y datos del usuario
        localStorage.setItem('token', datos.token);
        localStorage.setItem('user', JSON.stringify({
          id: datos.id,
          username: datos.username,
          email: datos.email,
          firstName: datos.firstName,
          lastName: datos.lastName,
          userType: datos.userType,
          isRoot: datos.isRoot
        }));
        
        this.actualizarNavbar();
        return { exito: true, datos };
      } else {
        const datosError = await respuesta.text();
        return { exito: false, error: datosError || 'Error al iniciar sesión' };
      }
    } catch (error) {
      console.error('Error de login:', error);
      return { exito: false, error: 'Error de conexión. Verifique que el servidor esté funcionando.' };
    }
  }

  // Método de registro
  async registrarse(datosUsuario) {
    try {
      const respuesta = await fetch(`${this.urlBase}/signup`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(datosUsuario),
      });

      if (respuesta.ok) {
        const datos = await respuesta.json();
        return { exito: true, datos };
      } else {
        const datosError = await respuesta.text();
        return { exito: false, error: datosError || 'Error al registrarse' };
      }
    } catch (error) {
      console.error('Error de registro:', error);
      return { exito: false, error: 'Error de conexión. Verifique que el servidor esté funcionando.' };
    }
  }

  // Método de cierre de sesión
  cerrarSesion() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    this.actualizarNavbar();
    this.redirigirA('index.html');
  }

  // Utilidad de redirección
  redirigirA(pagina) {
    window.location.href = pagina;
  }

  // Obtener headers de autorización para llamadas API
  obtenerHeadersAuth() {
    const token = localStorage.getItem('token');
    return token ? { 'Authorization': `Bearer ${token}` } : {};
  }
}

// Funciones globales para eventos HTML onclick
window.cerrarSesion = () => {
  if (window.gestorAuth) {
    window.gestorAuth.cerrarSesion();
  }
};

window.redirigirA = (pagina) => {
  window.location.href = pagina;
};

// Inicializar gestor de autenticación cuando el DOM esté cargado
document.addEventListener('DOMContentLoaded', () => {
  window.gestorAuth = new GestorAutenticacion();
});

// Exportar para uso en otros archivos
window.GestorAutenticacion = GestorAutenticacion;
