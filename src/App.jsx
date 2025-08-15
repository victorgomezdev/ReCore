import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './themes.css';
import { ThemeProvider } from './ThemeContext';
import HomePage from './pages/Home/Home';
import LoginPage from './pages/Login/Login';

const App = () => {
  return (
    <ThemeProvider>
      <Router>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<div style={{padding: '20px', textAlign: 'center'}}>Página de Registro (En desarrollo)</div>} />
        </Routes>
      </Router>
    </ThemeProvider>
  )
}

export default App