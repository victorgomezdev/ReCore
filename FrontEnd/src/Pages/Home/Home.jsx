import Footer from "../../components/Footer/Footer";
import Header from "../../components/Header/Header";
import SearchBar from "../../components/SearchBar/SearchBar";
import Tarjetas from "../../components/Tarjetas/Tarjetas";
import "./home.css";

const HomePage = () => {
  return (
    <>
      <div>
        <Header></Header>
      </div>
      <div>
        <SearchBar></SearchBar>
        <div className="contenido-container">
          <Tarjetas></Tarjetas>
        </div>
        <Footer></Footer>
      </div>
    </>
  );
};

export default HomePage;
