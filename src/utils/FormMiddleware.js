// Manejador universal de formularios
// Centraliza todas las llamadas API del frontend
class FormMiddleware {
  
  static async submit(endpoint, formData) {
    try {
      // Headers básicos
      const headers = {
        'Content-Type': 'application/json'
      };
      
      // Agregar token si existe
      const token = localStorage.getItem('token');
      if (token) {
        headers['Authorization'] = `Bearer ${token}`;
      }
      
      // Llamada API
      const response = await fetch(`http://localhost:8080${endpoint}`, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(formData)
      });
      
      // Procesar respuesta
      if (response.ok) {
        const data = await response.json();
        return { success: true, data: data };
      } else {
        const error = await response.json();
        alert('Error: ' + error.message);
        return { success: false, error: error };
      }
      
    } catch (error) {
      alert('Error de conexión: ' + error.message);
      return { success: false, error: error };
    }
  }
}

// Exportar para uso global
window.FormMiddleware = FormMiddleware;
