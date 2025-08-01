import InputForm from '../Inputs/InputText/InputText';
import './searchBar.css';
import { FaSearch } from "react-icons/fa";

const SearchBar = () => {
	return (<div className='search-bar'>
		<div className="search-bar-container">
			<div className="search-bar-title">Encuentra lo que buscas aquí!</div>
			<div className="search-bar-input-bar">
				<InputForm placeholder={"Buscar..."} icon={<FaSearch />}></InputForm>
			</div>
		</div>
	</div>);
}

export default SearchBar;