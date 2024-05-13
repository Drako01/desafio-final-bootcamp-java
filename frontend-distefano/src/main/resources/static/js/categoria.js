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

	const celdaNombre = document.createElement('td');
	celdaNombre.classList.add('celda-entera');
	const inputNombre = document.createElement('input');
	inputNombre.type = 'text';
	inputNombre.placeholder = 'Nombre de la categoría';
	inputNombre.style.width = '100%';
	inputNombre.style.height = '39px';
	inputNombre.style.borderRadius = '.25rem';
	inputNombre.style.border = 'none';
	inputNombre.style.backgroundColor = '#eeeeeed3';
	celdaNombre.appendChild(inputNombre);

	const celdaDescripcion = document.createElement('td');
	celdaDescripcion.classList.add('celda-entera');
	const inputDescripcion = document.createElement('input');
	inputDescripcion.type = 'text';
	inputDescripcion.placeholder = 'Descripción de la categoría';
	inputDescripcion.style.width = '100%';
	inputDescripcion.style.height = '39px';
	inputDescripcion.style.borderRadius = '.25rem';
	inputDescripcion.style.border = 'none';
	inputDescripcion.style.backgroundColor = '#eeeeeed3';
	inputDescripcion.style.verticalAlign = 'middle';
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
	});
}
