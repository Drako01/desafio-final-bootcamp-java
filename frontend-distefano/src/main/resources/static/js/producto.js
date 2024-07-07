let categorias = [];

fetch('/backend/categorias/json')
	.then(response => {
		if (!response.ok) {
			throw new Error('Error en la solicitud AJAX');
		}
		return response.json();
	})
	.then(data => {
		categorias = data;
		return data;
	})
	.catch(error => {
		console.error('Error en la solicitud AJAX:', error);
	});

let productos = [];

fetch('/backend/productos/json')
	.then(response => {
		if (!response.ok) {
			throw new Error('Error en la solicitud AJAX');
		}
		return response.json();
	})
	.then(data => {
		productos = data;
		productos.forEach(producto => {
			if (producto.stock === 0) {
				const fila = document.querySelector(`tr[data-id="${producto.id_producto}"]`);
				fila.style.backgroundColor = '#dddddd';
				fila.style.color = 'red';
			}
		});
	})
	.catch(error => {
		console.error('Error en la solicitud AJAX:', error);
	});




function inicializarEventoProducto() {
	let botonAgregarProducto = document.getElementById("boton-agregar-producto");
	botonAgregarProducto.addEventListener('click', () => {
		if (!botonAgregarProducto.classList.contains('disabled')) {
			botonAgregarProducto.classList.add('disabled');
			agregarNuevoProducto();
		}

	});

	let botonesModificarProd = document.querySelectorAll(".btn-modificar-prod");

	botonesModificarProd.forEach(boton => {
		boton.addEventListener('click', () => {
			modificarProducto(boton.closest('tr'));
		});
	});

	let botonesEliminarProd = document.querySelectorAll(".btn-eliminar-prod");


	botonesEliminarProd.forEach(boton => {
		boton.addEventListener('click', () => {
			eliminarProducto(boton.closest('tr'));
		});
	});

}

function crearEntornoProducto() {
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



	const crearInput = (type, placeholder) => {
		const input = document.createElement('input');
		input.type = type;
		input.required = true;
		input.style.cssText = estiloInput;
		input.placeholder = placeholder;
		return input;
	};

	const crearSelect = (id, name, options) => {
		const select = document.createElement('select');
		select.classList.add('form-control');
		select.id = id;
		select.name = name;
		select.required = true;

		options.forEach(option => {
			const optionElement = document.createElement('option');
			optionElement.value = option.id_categoria;
			optionElement.textContent = option.nombre;
			select.appendChild(optionElement);
		});

		return select;
	};

	const celdaFoto = crearCelda();
	const inputFoto = crearInput('text', 'URL de la foto');
	celdaFoto.appendChild(inputFoto);

	const celdaNombre = crearCelda();
	const inputNombre = crearInput('text', 'Nombre');
	celdaNombre.appendChild(inputNombre);

	const celdaDescripcion = crearCelda();
	const inputDescripcion = crearInput('text', 'Descripción');
	celdaDescripcion.appendChild(inputDescripcion);

	const celdaStock = crearCelda();
	const inputStock = crearInput('number', 'Stock');
	inputStock.min = 0;
	celdaStock.appendChild(inputStock);

	const celdaPrecio = crearCelda();
	const inputPrecio = crearInput('number', 'Precio');
	inputPrecio.min = 0;
	celdaPrecio.appendChild(inputPrecio);

	const celdaCategoria = crearCelda();
	const selectCategoria = crearSelect('categoriaId', 'categoriaId', categorias);
	celdaCategoria.appendChild(selectCategoria);


	const celdaAcciones = crearCelda();
	celdaAcciones.setAttribute('colspan', '2');
	const botonGuardar = document.createElement('button');
	botonGuardar.textContent = 'Guardar';
	botonGuardar.type = 'button';
	botonGuardar.classList.add('btn', 'btn-success', 'btn-guardar');
	celdaAcciones.appendChild(botonGuardar);

	const anchoCeldaFoto = document.querySelector('table tbody tr:first-child td:nth-child(1)').offsetWidth;
	const anchoCeldaNombre = document.querySelector('table tbody tr:first-child td:nth-child(2)').offsetWidth;
	const anchoCeldaDescripcion = document.querySelector('table tbody tr:first-child td:nth-child(3)').offsetWidth;
	const anchoCeldaStock = document.querySelector('table tbody tr:first-child td:nth-child(4)').offsetWidth;
	const anchoCeldaPrecio = document.querySelector('table tbody tr:first-child td:nth-child(5)').offsetWidth;
	const anchoCeldaCategoria = document.querySelector('table tbody tr:first-child td:nth-child(6)').offsetWidth;
	const anchoCeldaAcciones = document.querySelector('table tbody tr:first-child td:nth-child(7)').offsetWidth;

	celdaFoto.style.width = anchoCeldaFoto + 'px';
	celdaNombre.style.width = anchoCeldaNombre + 'px';
	celdaDescripcion.style.width = anchoCeldaDescripcion + 'px';
	celdaStock.style.width = anchoCeldaStock + 'px';
	celdaPrecio.style.width = anchoCeldaPrecio + 'px';
	celdaCategoria.style.width = anchoCeldaCategoria + 'px';
	celdaAcciones.style.width = anchoCeldaAcciones + 'px';


	return { celdaFoto, inputFoto, celdaNombre, inputNombre, celdaDescripcion, inputDescripcion, celdaStock, inputStock, celdaPrecio, inputPrecio, celdaCategoria, selectCategoria, celdaAcciones, botonGuardar };
}


function agregarNuevoProducto() {
	const tabla = document.querySelector('table tbody');
	const primeraFila = tabla.firstChild;
	const nuevaFila = document.createElement('tr');

	const { celdaFoto, inputFoto, celdaNombre, inputNombre, celdaDescripcion, inputDescripcion, celdaStock, inputStock, celdaPrecio, inputPrecio, celdaCategoria, celdaAcciones, botonGuardar } = crearEntornoProducto();

	nuevaFila.appendChild(celdaFoto);
	nuevaFila.appendChild(celdaNombre);
	nuevaFila.appendChild(celdaDescripcion);
	nuevaFila.appendChild(celdaStock);
	nuevaFila.appendChild(celdaPrecio);
	nuevaFila.appendChild(celdaCategoria);
	nuevaFila.appendChild(celdaAcciones);

	tabla.insertBefore(nuevaFila, primeraFila);

	function crearFormData() {
		const fotoProducto = inputFoto.value;
		const nombreProducto = inputNombre.value;
		const descripcionProducto = inputDescripcion.value;
		let stockProducto = inputStock.value;
		let precioProducto = inputPrecio.value.trim();

		if (precioProducto === "" || isNaN(parseFloat(precioProducto))) {
			precioProducto = 0;
		}

		if (stockProducto === "" || isNaN(parseFloat(stockProducto))) {
			stockProducto = 0;
		}
		const selectCategoria = document.getElementById('categoriaId');
		const categoriaId = selectCategoria.value;

		const formData =
			'nombre=' + encodeURIComponent(nombreProducto)
			+ '&descripcion=' + encodeURIComponent(descripcionProducto)
			+ '&precio=' + encodeURIComponent(precioProducto)
			+ '&imagen=' + encodeURIComponent(fotoProducto)
			+ '&stock=' + encodeURIComponent(stockProducto)
			+ '&categoriaId=' + encodeURIComponent(categoriaId);

		return formData;
	}

	botonGuardar.addEventListener('click', () => {
		const formData = crearFormData();
		if (formData) {
			fetch('/backend/productos/agregar/', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				},
				body: formData
			})
				.then(response => {
					if (response.ok) {
						window.location.href = '/backend/productos/';
					}
					botonAgregarProducto.classList.remove('disabled');
				})
				.catch(error => {
					console.error('Error al guardar el producto:', error);
					botonAgregarProducto.classList.remove('disabled');
				});
		}
	});
}


function modificarProducto(filaProducto) {
	const idProducto = filaProducto.dataset.id;
	const fotoInicial = filaProducto.querySelector('td:first-child img').getAttribute('src');
	const nombreActual = filaProducto.querySelector('td:nth-child(2)').textContent;
	const descripcionActual = filaProducto.querySelector('td:nth-child(3)').textContent;
	const stockActual = filaProducto.querySelector('td:nth-child(4)').textContent;
	const precioActual = filaProducto.querySelector('td:nth-child(5)').textContent.trim();

	const { celdaFoto, inputFoto, celdaNombre, inputNombre, celdaDescripcion, inputDescripcion, celdaStock, inputStock, celdaPrecio, inputPrecio, celdaCategoria, selectCategoria, celdaAcciones, botonGuardar } = crearEntornoProducto(idProducto);


	inputFoto.value = fotoInicial;
	inputNombre.value = nombreActual;
	inputDescripcion.value = descripcionActual;
	inputStock.value = stockActual;

	const precioNumerico = parseFloat(precioActual.replace(/[^0-9.-]+/g, ""));
	inputPrecio.value = precioNumerico.toFixed(2);


	const nuevaFila = document.createElement('tr');
	nuevaFila.setAttribute('th:data-id', idProducto);
	nuevaFila.appendChild(celdaFoto);
	nuevaFila.appendChild(celdaNombre);
	nuevaFila.appendChild(celdaDescripcion);
	nuevaFila.appendChild(celdaStock);
	nuevaFila.appendChild(celdaPrecio);
	nuevaFila.appendChild(celdaCategoria);
	nuevaFila.appendChild(celdaAcciones);
	nuevaFila.setAttribute('th:data-id', idProducto);

	const tabla = document.querySelector('table tbody');
	tabla.replaceChild(nuevaFila, filaProducto);

	botonGuardar.addEventListener('click', () => {
		const fotoProducto = inputFoto.value;
		const nombreProducto = inputNombre.value;
		const descripcionProducto = inputDescripcion.value;
		let stockProducto = inputStock.value;
		let precioProducto = inputPrecio.value.trim();
		const categoriaId = selectCategoria.value;

		if (precioProducto === "" || isNaN(parseFloat(precioProducto))) {
			precioProducto = 0;
		}

		if (stockProducto === "") {
			stockProducto = 0;
		}
		const formData =
			'nombre=' + encodeURIComponent(nombreProducto) +
			'&descripcion=' + encodeURIComponent(descripcionProducto) +
			'&precio=' + encodeURIComponent(precioProducto) +
			'&stock=' + encodeURIComponent(stockProducto) +
			'&imagen=' + encodeURIComponent(fotoProducto) +
			'&categoriaId=' + encodeURIComponent(categoriaId);

		if (formData) {
			fetch(`/backend/productos/modificar/${idProducto}`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				},
				body: formData
			})
				.then(response => {
					if (response.ok) {
						window.location.href = '/backend/productos/';
					}
				})
				.catch(error => {
					console.error('Error al modificar el producto:', error);
				});
		}
	});
}


function eliminarProducto(fila) {
	const idProducto = fila.dataset.id;
	let modal = document.getElementById('modalEliminarProducto');
	modal.classList.add('show');
	modal.style.display = 'block';
	const confirmarEliminacionBtn = document.getElementById('confirmarEliminacionProducto');



	if (modal && confirmarEliminacionBtn) {
		confirmarEliminacionBtn.addEventListener('click', () => {
			fetch(`/backend/productos/eliminar/${idProducto}`)
				.then(response => {
					if (response.ok) {
						window.location.href = '/backend/productos/';
					}
				})
				.catch(error => {

					console.error('Error al eliminar la producto:', error);
				});
		});

		$(modal).modal('show');
	} else {
		$(modal).modal('hide');
	}

	const cancelarBtn = document.querySelector('.btn-secondary');
	if (cancelarBtn) {
		cancelarBtn.addEventListener('click', () => {
			$(modal).modal('hide');
		});
	}

	const closeModalBtn = document.querySelector('.close');
	if (closeModalBtn) {
		closeModalBtn.addEventListener('click', () => {
			$(modal).modal('hide');
		});
	}
}

