import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './themes.css';
import { ThemeProvider } from './ThemeContext';
import HomePage from './pages/Home/Home';
import LoginPage from './pages/Login/Login';
import RegisterPage from './pages/Register/Register';
import AdminPanel from './pages/Admin/AdminPanel';

const App = () => {
  return (
    <ThemeProvider>
      <Router>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/adminpanel" element={<AdminPanel />} />
        </Routes>
      </Router>
    </ThemeProvider>
  )
}

export default App