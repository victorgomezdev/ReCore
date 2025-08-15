import './InputText.css';

const InputText = ({ label, placeholder, icon, name = "input", type = "text" }) => {
	return (
		<div>
			<div className="form-group">
				<label htmlFor={name} className="form-label">
					{label}
				</label>
				<div className="input-icon-wrapper">

					<input
						type={type}
						className="form-field"
						placeholder={placeholder}
						name={name}
						id={name}
						required
					/>
					{icon && <div className="input-icon">{icon}</div>}
				</div>
			</div>
		</div>
	);
};

export default InputText;
