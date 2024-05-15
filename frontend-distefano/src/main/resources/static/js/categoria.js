function inicializarEventoCategoria() {
	let botonAgregarCat = document.getElementById("boton-agregar-cat");
	botonAgregarCat.addEventListener('click', () => {
		if (!botonAgregarCat.classList.contains('disabled')) {
			botonAgregarCat.classList.add('disabled');
			agregarNuevaCategoria();
		}
	});

}



function agregarNuevaCategoria() {
	const tabla = document.querySelector('table tbody');
	const primeraFila = tabla.firstChild;
	const nuevaFila = document.createElement('tr');

	const estiloInput = `
        width: 100%;
        height: 39px;
        border-radius: .25rem;
        border: none;
        background-color: #eeeeeed3;
        vertical-align: middle;
    `;

	const crearCelda = () => {
		const celda = document.createElement('td');
		return celda;
	};

	const crearInput = () => {
		const input = document.createElement('input');
		input.type = 'text';
		input.required = true;
		input.style.cssText = estiloInput;
		return input;
	};

	const celdaNombre = crearCelda();
	celdaNombre.classList.add('celda-entera');
	const inputNombre = crearInput();
	celdaNombre.appendChild(inputNombre);

	const celdaDescripcion = crearCelda();
	celdaDescripcion.classList.add('celda-entera');
	const inputDescripcion = crearInput();

	celdaDescripcion.appendChild(inputDescripcion);

	const celdaAcciones = document.createElement('td');
	celdaAcciones.setAttribute('colspan', '2');
	const botonGuardar = document.createElement('button');
	botonGuardar.textContent = 'Guardar';
	botonGuardar.classList.add('btn', 'btn-success', 'btn-guardar');
	celdaAcciones.appendChild(botonGuardar);

	const anchoCeldaNombre = document.querySelector('table tbody tr:first-child td:nth-child(1)').offsetWidth;
	const anchoCeldaDescripcion = document.querySelector('table tbody tr:first-child td:nth-child(2)').offsetWidth;
	const anchoCeldaAcciones = document.querySelector('table tbody tr:first-child td:nth-child(3)').offsetWidth;

	celdaNombre.style.width = anchoCeldaNombre + 'px';
	celdaDescripcion.style.width = anchoCeldaDescripcion + 'px';
	celdaAcciones.style.width = anchoCeldaAcciones + 'px';

	nuevaFila.appendChild(celdaNombre);
	nuevaFila.appendChild(celdaDescripcion);
	nuevaFila.appendChild(celdaAcciones);

	tabla.insertBefore(nuevaFila, primeraFila);

	botonGuardar.addEventListener('click', () => {
		const nombreCategoria = inputNombre.value;
		const descripcionCategoria = inputDescripcion.value;
		const formData = 'nombre=' + encodeURIComponent(nombreCategoria)
			+ '&descripcion=' + encodeURIComponent(descripcionCategoria);
		if (nombreCategoria != '' && descripcionCategoria != '') {
			if (formData) {
				fetch('/backend/categorias/agregar/', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded'
					},
					body: formData.toString()
				})
					.then(response => {
						if (response.ok) {
							window.location.href = '/backend/categorias/';
						}
						botonAgregarCat.classList.remove('disabled');
					})
					.catch(error => {
						console.error('Error al guardar la categoría:', error);
						botonAgregarCat.classList.remove('disabled');
					});
			}
		}
	});
}
