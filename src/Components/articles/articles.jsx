import categoriaPng from '../../assets/categoria.png';
import './articles.css';
const ArticlesBox = () => {
	return (<>
		<div className='contenedor-categorias-titulo'>Algunos de nuestros artículos</div>
		<div className='contenedor-categorias'>

			<div className='contenedor-categoria'>
				<img src={categoriaPng} alt="" />
				<div>Article name</div>
				<div>Article info</div>
			</div>
			<div className='contenedor-categoria'>
				<img src={categoriaPng} alt="" />
				<div>Article name</div>
				<div>Article info</div>
			</div>
			<div className='contenedor-categoria'>
				<img src={categoriaPng} alt="" />
				<div>Article name</div>
				<div>Article info</div>
			</div>
			<div className='contenedor-categoria'>
				<img src={categoriaPng} alt="" />
				<div>Article name</div>
				<div>Article info</div>
			</div>

		</div></>
	);
}

export default ArticlesBox;