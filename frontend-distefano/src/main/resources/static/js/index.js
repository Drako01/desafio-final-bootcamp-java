window.onload = function() {
	if (window.location.pathname === '/backend/categorias/') {
		inicializarEventoCategoria();
	} else if (window.location.pathname === '/backend/productos/') {
		inicializarEventoProducto();
	} else if (window.location.pathname === '/carrito/') {
		actualizarCarritoTable();
	}
	obtenerFechaYHora();
};

let userId = 1;
let cantidadTotal = 0;
let precioTotal = 0;
let carritoTable = document.querySelector('#carrito-table');
let botonComprarCarrito = document.querySelector('#btn-compar-carrito');
const fechaHoraElement = document.getElementById('fechaHora');

let carrito = JSON.parse(localStorage.getItem('carrito')) || [];

$(document).ready(function() {
	$('[data-toggle="modal"]').modal();
	cargarCarritoDesdeLocalStorage();
	actualizarCarrito();
});

function obtenerFechaYHora() {
	fetch('/fecha/')
		.then(response => response.text())
		.then(data => {
			const fechaHoraString = data.match(/datetime: (\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}.\d+-\d{2}:\d{2})/);

			if (fechaHoraString && fechaHoraString[1]) {
				const datetime = new Date(fechaHoraString[1]);

				if (!isNaN(datetime.getTime())) {
					const hora = datetime.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
					const fecha = datetime.toLocaleDateString([], { day: '2-digit', month: '2-digit', year: 'numeric' });

					fechaHoraElement.textContent = `${hora} hr | ${fecha}`;
				} else {
					console.error('Error: Fecha y hora no válidas');
				}
			} else {
				console.error('Error: No se encontró la cadena de fecha y hora en el formato esperado');
			}
		})
		.catch(error => {
			console.error('Error al obtener la fecha y hora:', error);
		});
}

function cargarCarritoDesdeLocalStorage() {
	const carritoGuardado = localStorage.getItem('carrito');
	if (carritoGuardado) {
		carrito = JSON.parse(carritoGuardado);
	}
}

function actualizarCarrito() {
	cantidadTotal = 0;
	precioTotal = 0;

	carrito.forEach(producto => {
		cantidadTotal += producto.cantidad;
		precioTotal += producto.subtotal;
	});

	document.querySelector('.item-carrito p').innerText = cantidadTotal;
	document.querySelector('.item-carrito p span').innerText = precioTotal;
}


function actualizarCarritoTable() {
	let cantidadTotal = 0;
	let precioTotal = 0;
	carritoTable.innerHTML = '';

	carrito.forEach(producto => {
		const row = document.createElement('tr');

		const nombreCell = document.createElement('td');
		nombreCell.textContent = producto.nombre;
		row.appendChild(nombreCell);

		const cantidadCell = document.createElement('td');
		cantidadCell.textContent = producto.cantidad + ' Unidades';
		row.appendChild(cantidadCell);

		const precioCell = document.createElement('td');
		precioCell.textContent =  '$ ' + producto.precio;
		row.appendChild(precioCell);

		const subtotalCell = document.createElement('td');
		subtotalCell.textContent = '$ ' + producto.subtotal;
		row.appendChild(subtotalCell);
		
		const eliminarCell = document.createElement('td');
		eliminarCell.innerHTML = `<td class="acciones-table"><a
									href="#"
									class="btn btn-danger btn-eliminar-prod-carrito"> 
									<i class="fa fa-trash"></i>
								</a></td>`;
		row.appendChild(eliminarCell);
		
		
		

		carritoTable.appendChild(row);

		cantidadTotal += producto.cantidad;
		precioTotal += producto.subtotal;
	});

	let totalAmount = document.getElementById('total-amount')
	totalAmount.innerText =  '$ ' + precioTotal;
	totalAmount.style.color = 'green';
	totalAmount.style.fontWeight = 'bold';
	totalAmount.style.fontSize = '1.2rem';
}

fetch('/backend/productos/json')
	.then(response => {
		if (!response.ok) {
			throw new Error('Error en la solicitud AJAX');
		}
		return response.json();
	})
	.then(data => {
		productos = data;
		
	})
	.catch(error => {
		console.error('Error en la solicitud AJAX:', error);
	});
