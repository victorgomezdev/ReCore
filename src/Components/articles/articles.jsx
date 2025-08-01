import categoriaPng from '../../assets/categoria.png';
import './articles.css';
const ArticlesBox = () => {
	return (<>
		<div className='contenedor-articulos-titulo'>Algunos de nuestros artículos</div>
		<div className='contenedor-articulos'>

			<div className='contenedor-articulo'>
				<img src={categoriaPng} alt="" />
				<div>Article name</div>
				<div>Article info</div>
			</div>
			<div className='contenedor-articulo'>
				<img src={categoriaPng} alt="" />
				<div>Article name</div>
				<div>Article info</div>
			</div>
			<div className='contenedor-articulo'>
				<img src={categoriaPng} alt="" />
				<div>Article name</div>
				<div>Article info</div>
			</div>
			<div className='contenedor-articulo'>
				<img src={categoriaPng} alt="" />
				<div>Article name</div>
				<div>Article info</div>
			</div>

		</div></>
	);
}

export default ArticlesBox;