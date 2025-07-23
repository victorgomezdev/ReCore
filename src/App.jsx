import Navbar from './Components/Navbar/Navbar'
import './themes.css';
import { ThemeProvider } from './ThemeContext';
import SearchBar from './Components/SearchBar/SearchBar';

const App = () => {

  return (
    <ThemeProvider>
      <div className='container'>
        <Navbar />
        <div className={`container-body`}>
          <section className="search-section">
            <SearchBar></SearchBar>
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
