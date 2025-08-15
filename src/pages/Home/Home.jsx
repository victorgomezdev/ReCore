import Navbar from '../../Components/Navbar/Navbar';
import SearchBar from '../../Components/SearchBar/SearchBar';
import CategoriesBox from '../../Components/categories/categories';
import ArticlesBox from '../../Components/articles/articles';
import Footer from '../../Components/footer/Footer';
import { ThemeProvider } from '../../ThemeContext';

const HomePage = () => {
  return (
    <ThemeProvider>
      <div className='container'>
        <Navbar />
        <div className={`container-body`}>
          <section className="search-section">
            <SearchBar></SearchBar>
          </section>

          <section className="categories-section">
            <CategoriesBox></CategoriesBox>
          </section>

          <section className="recommendations-section">
            <ArticlesBox></ArticlesBox>
          </section>
        </div>
        <Footer></Footer>
      </div>
    </ThemeProvider>
  );
};

export default HomePage;
