import './InputTextFlat.css';

const InputTextFlat = ({ label, placeholder, icon, name = "input", type = "text", onchange = null }) => {
	return (
		<div>
			<div className="form-group-flat">
				<label htmlFor={name} className="form-label-flat">
					{label}
				</label>
				<div className="input-icon-wrapper">

					<input
						type={type}
						className="form-field-flat"
						placeholder={placeholder}
						name={name}
						id={name}
						onChange={onchange}
						required
					/>
					{icon && <div className="input-icon">{icon}</div>}
				</div>
			</div>
		</div>
	);
};

export default InputTextFlat;
