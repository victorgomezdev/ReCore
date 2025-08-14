// Lógica principal de la aplicación
document.addEventListener('DOMContentLoaded', async () => {
  // Inicializar componentes
  inicializarNavbar();
  await cargarCategorias();
  await cargarArticulos();
  configurarBusqueda();
});

// Inicializar funcionalidad del navbar (tu estructura original)
function inicializarNavbar() {
  const menuBurger = document.getElementById('menu-burger');
  const contenedorBotones = document.getElementById('contenedor-botones');

  if (menuBurger && contenedorBotones) {
    menuBurger.addEventListener('click', () => {
      if (contenedorBotones.classList.contains('inactive')) {
        contenedorBotones.classList.remove('inactive');
        contenedorBotones.classList.add('active');
      } else {
        contenedorBotones.classList.remove('active');
        contenedorBotones.classList.add('inactive');
      }
    });

    // Cerrar menú al hacer clic fuera (en dispositivos móviles)
    document.addEventListener('click', (e) => {
      if (!menuBurger.contains(e.target) && !contenedorBotones.contains(e.target)) {
        contenedorBotones.classList.remove('active');
        contenedorBotones.classList.add('inactive');
      }
    });
  }
}

// Cargar y mostrar categorías (estructura original del componente React)
async function cargarCategorias() {
  const contenedorCategorias = document.getElementById('contenedor-categorias');
  if (!contenedorCategorias) return;

  try {
    const categorias = await window.gestorAPI.obtenerCategorias();
    
    contenedorCategorias.innerHTML = categorias.map(categoria => `
      <div class="contenedor-categoria">
        <img src="src/assets/categoria.png" alt="">
        <div>${categoria.titulo}</div>
        <div>${categoria.descripcion}</div>
      </div>
    `).join('');
  } catch (error) {
    console.error('Error al cargar categorías:', error);
    contenedorCategorias.innerHTML = '<p>Error al cargar las categorías</p>';
  }
}

// Cargar y mostrar artículos (estructura original del componente React)
async function cargarArticulos() {
  const contenedorArticulos = document.getElementById('contenedor-articulos');
  if (!contenedorArticulos) return;

  try {
    const articulos = await window.gestorAPI.obtenerArticulos();
    
    contenedorArticulos.innerHTML = articulos.map(articulo => `
      <div class="contenedor-categoria">
        <img src="src/assets/categoria.png" alt="">
        <div>${articulo.titulo}</div>
        <div>${articulo.descripcion}</div>
      </div>
    `).join('');
  } catch (error) {
    console.error('Error al cargar artículos:', error);
    contenedorArticulos.innerHTML = '<p>Error al cargar los artículos</p>';
  }
}

// Configurar funcionalidad de búsqueda
function configurarBusqueda() {
  const inputBusqueda = document.getElementById('search-input');
  const botonBusqueda = document.querySelector('.input-icon');

  if (inputBusqueda) {
    inputBusqueda.addEventListener('keypress', (e) => {
      if (e.key === 'Enter') {
        realizarBusqueda();
      }
    });
  }

  if (botonBusqueda) {
    botonBusqueda.addEventListener('click', realizarBusqueda);
  }
}

// Realizar búsqueda
async function realizarBusqueda() {
  const inputBusqueda = document.getElementById('search-input');
  if (!inputBusqueda) return;

  const consulta = inputBusqueda.value.trim();
  if (!consulta) {
    alert('Por favor ingrese un término de búsqueda');
    return;
  }

  try {
    console.log(`Buscando: ${consulta}`);
    const resultados = await window.gestorAPI.buscar(consulta);
    
    // Por ahora, solo registrar los resultados
    // En una implementación real, mostrarías los resultados
    console.log('Resultados de búsqueda:', resultados);
    
    // Mostrar una alerta simple para demostración
    alert(`Se encontraron ${resultados.length} resultados para "${consulta}"`);
  } catch (error) {
    console.error('Error en búsqueda:', error);
    alert('Error al realizar la búsqueda');
  }
}

// Manejador de selección de categoría
function seleccionarCategoria(idCategoria) {
  console.log(`Categoría seleccionada: ${idCategoria}`);
  // Aquí normalmente navegarías a una página de categoría o filtrarías contenido
  alert(`Navegando a la categoría ${idCategoria}`);
}

// Manejador de selección de artículo
function seleccionarArticulo(idArticulo) {
  console.log(`Artículo seleccionado: ${idArticulo}`);
  // Aquí normalmente navegarías a la página del artículo
  alert(`Abriendo artículo ${idArticulo}`);
}

// Función utilitaria para formatear fechas
function formatearFecha(cadenaFecha) {
  const fecha = new Date(cadenaFecha);
  return fecha.toLocaleDateString('es-ES', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  });
}

// Funciones globales para eventos HTML onclick
window.realizarBusqueda = realizarBusqueda;
window.seleccionarCategoria = seleccionarCategoria;
window.seleccionarArticulo = seleccionarArticulo;
