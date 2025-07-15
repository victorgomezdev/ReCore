import { useEffect, useState } from 'react'
import Navbar from './Components/Navbar/Navbar'
import './themes.css';
import { ThemeProvider } from './ThemeContext';

const App = () => {

  return (
    <ThemeProvider>
      <div className='container'>
        <Navbar />
        <div className={`container-body`}>
          <section className="search-section">
            {/* Buscador */}
          </section>

          <section className="categories-section">
            {/* Categorías */}
          </section>

          <section className="recommendations-section">
            {/* Productos recomendados */}
          </section>
        </div>
      </div>
    </ThemeProvider>
  )
}

export default App
