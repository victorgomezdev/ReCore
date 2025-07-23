import categoriaPng from '../../assets/categoria.png';
import './categories.css';
const CategoriesBox = () => {
	return (<>
		<div className='contenedor-categorias-titulo'>Algunas de nuestras categorías</div>
		<div className='contenedor-categorias'>

			<div className='contenedor-categoria'>
				<img src={categoriaPng} alt="" />
				<div>Categoria name</div>
				<div>Categori info</div>
			</div>
			<div className='contenedor-categoria'>
				<img src={categoriaPng} alt="" />
				<div>Categoria name</div>
				<div>Categori info</div>
			</div>
			<div className='contenedor-categoria'>
				<img src={categoriaPng} alt="" />
				<div>Categoria name</div>
				<div>Categori info</div>
			</div>
			<div className='contenedor-categoria'>
				<img src={categoriaPng} alt="" />
				<div>Categoria name</div>
				<div>Categori info</div>
			</div>

		</div></>
	);
}

export default CategoriesBox;