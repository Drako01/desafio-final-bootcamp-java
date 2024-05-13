function inicializarEventos() {
	let botonAgregarCat = document.getElementById("boton-agregar-cat");

	botonAgregarCat.addEventListener('click', () => {
		agregarNuevaCategoria();
	});
}

function agregarNuevaCategoria() {
	const tabla = document.querySelector('table tbody');
	const nuevaFila = document.createElement('tr');

	const celdaNombre = document.createElement('td');
	const inputNombre = document.createElement('input');
	inputNombre.type = 'text';
	inputNombre.placeholder = 'Nombre de la categoría';
	celdaNombre.appendChild(inputNombre);

	const celdaDescripcion = document.createElement('td');
	const inputDescripcion = document.createElement('input');
	inputDescripcion.type = 'text';
	inputDescripcion.placeholder = 'Descripción de la categoría';
	celdaDescripcion.appendChild(inputDescripcion);

	const celdaAcciones = document.createElement('td');
	celdaAcciones.setAttribute('colspan', '2');
	const botonGuardar = document.createElement('button');
	botonGuardar.textContent = 'Guardar';
	botonGuardar.classList.add('btn', 'btn-success', 'btn-guardar');
	celdaAcciones.appendChild(botonGuardar);

	nuevaFila.appendChild(celdaNombre);
	nuevaFila.appendChild(celdaDescripcion);
	nuevaFila.appendChild(celdaAcciones);

	tabla.appendChild(nuevaFila);

	botonGuardar.addEventListener('click', () => {
		const nombreCategoria = inputNombre.value;
		const descripcionCategoria = inputDescripcion.value;
		const formData = 'nombre=' + encodeURIComponent(nombreCategoria) + '&descripcion=' + encodeURIComponent(descripcionCategoria);
		fetch('/backend/categorias/agregar/', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			body: formData.toString()
		})
			.then(response => {
				console.log('Categoría guardada:', response);

				if (response.ok) {
					window.location.href = '/backend/categorias/';
				}
			})
			.catch(error => {
				console.error('Error al guardar la categoría:', error);
			});
	});
}

window.onload = function() {
	inicializarEventos();
};