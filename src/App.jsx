import { useEffect, useState } from 'react'
import Navbar from './Components/Navbar/Navbar'

const App = () => {

  // Valor incial, recuperado de localStorage
  const current_theme = localStorage.getItem('current_theme');
  // Variable theme, su funcion setTheme para actualizar su valor 
  // recuperado de localStorage, y en caso de no haber, light por defecto
  const [theme, setTheme] = useState(current_theme ? current_theme : 'light');

  // Cuando el valor de theme se modifica, se guarda en localStorage
  useEffect(() => {
    localStorage.setItem('current_theme', theme);
  }, [theme])

  return (
    <div className={`container ${theme}`}>
      <Navbar theme={theme} setTheme={setTheme} />
    </div>
  )
}

export default App
