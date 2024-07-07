const token = localStorage.getItem('token');
function getToken() {
	return token;
}

function appendLink() {
	const logLink = document.getElementById('li-sesion');
	if (!token) {
		window.location.href = '/login';
	} else {
		logLink.innerHTML = `<a class="nav-link" aria-current="page" 
						href="#" onClick="reenviarLogin(event)">Logout</a>`

	}
}

function reenviarLogin(event) {
	event.preventDefault();

	fetch('http://localhost:8080/auth/logout')
		.then(() => {
			localStorage.removeItem('token');
			window.location.href = '/login';
		})
		.catch(error => {
			console.error('Error during logout:', error);
		});
}


window.onload = function() {
	appendLink();
	getToken();
	obtenerFechaYHora();
	if (window.location.pathname === '/backend/categorias/') {
		inicializarEventoCategoria();

	} else if (window.location.pathname === '/backend/productos/') {
		inicializarEventoProducto();

	} else if (window.location.pathname === '/carrito/') {
		actualizarCarritoTable();
		appendLink();
	}

};

let userId = 1;
let cantidadTotal = 0;
let precioTotal = 0;
let carritoTable;
let botonComprarCarrito;
const fechaHoraElement = document.getElementById('fechaHora');
let carrito = JSON.parse(localStorage.getItem('carrito')) || [];

$(document).ready(function() {
	$('[data-toggle="modal"]').modal();
	cargarCarritoDesdeLocalStorage();
	actualizarCarrito();

	carritoTable = document.querySelector('#carrito-table');
	botonComprarCarrito = document.querySelector('#btn-compar-carrito');

	function crearFormDataCart() {
		const carrito = JSON.parse(localStorage.getItem('carrito')) || [];
		const fecha_pedido = new Date().toISOString();
		const estado = "PENDIENTE";
		let precioTotalCart = 0;

		

		precioTotalCart = carrito.reduce((total, item) => total + item.subtotal, 0);

		const formDataCart = {
			user: { id: userId },
			fecha_pedido: fecha_pedido,
			estado: estado,
			precio_total: precioTotalCart			
		};

		return JSON.stringify(formDataCart);
	}


	if (botonComprarCarrito) {
		botonComprarCarrito.addEventListener('click', () => {
			const formDataCart = crearFormDataCart();
			if (formDataCart) {
				fetch('/carritos/', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json',
						'Authorization': 'Bearer ' + token
					},
					body: formDataCart
				})
					.then(response => {
						if (response.ok) {
							console.log(formDataCart)
							return response.json();
						} else {
							throw new Error('Error en la respuesta del servidor');
						}
					})
					.then(data => {
						console.log('Carrito guardado con éxito:', data);
						localStorage.setItem('carrito', JSON.stringify([]));
						actualizarCarrito();
						actualizarCarritoTable();
						window.location.href = '/carrito/';
					})
					.catch(error => {
						console.error('Error al guardar el carrito:', error);
					});
				localStorage.setItem('carrito', JSON.stringify([]));
			}
		});
	}

	document.querySelectorAll('.btn-eliminar-prod-carrito').forEach(button => {
		button.addEventListener('click', mostrarModalConfirmacionEliminar);
	});

	document.getElementById('confirmDeleteButton').addEventListener('click', confirmarEliminarProducto);

	document.getElementById('cancelDeleteButton').addEventListener('click', () => {
		$('#confirmDeleteModal').modal('hide');
	});

	document.querySelector('.modal .close').addEventListener('click', () => {
		$('#confirmDeleteModal').modal('hide');
	});
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
	botonComprarCarrito.classList.add('right');
	carrito.forEach((producto, index) => {
		const row = document.createElement('tr');

		const imagenCell = document.createElement('td');
		const imgElement = document.createElement('img');
		imgElement.src = producto.imagen;
		imagenCell.classList.add('d-flex');
		imagenCell.style.justifyContent = 'center';
		imgElement.style.alignItems = 'center';
		imgElement.style.width = '40px';
		imagenCell.appendChild(imgElement);
		row.appendChild(imagenCell);

		const nombreCell = document.createElement('td');
		nombreCell.textContent = producto.nombre;
		row.appendChild(nombreCell);

		const cantidadCell = document.createElement('td');
		cantidadCell.textContent = producto.cantidad + ' Unidades';
		row.appendChild(cantidadCell);

		const precioCell = document.createElement('td');
		precioCell.textContent = '$ ' + producto.precio;
		row.appendChild(precioCell);

		const subtotalCell = document.createElement('td');
		subtotalCell.textContent = '$ ' + producto.subtotal;
		row.appendChild(subtotalCell);

		const eliminarCell = document.createElement('td');
		eliminarCell.innerHTML = `<td class="acciones-table"><a
                                    href="#"
                                    class="btn btn-danger btn-eliminar-prod-carrito" data-index="${index}" data-toggle="modal" data-target="#confirmDeleteModal"> 
                                    <i class="fa fa-trash"></i>
                                </a></td>`;
		row.appendChild(eliminarCell);

		carritoTable.appendChild(row);

		cantidadTotal += producto.cantidad;
		precioTotal += producto.subtotal;
	});

	let totalAmount = document.getElementById('total-amount');
	totalAmount.innerText = '$ ' + precioTotal;
	totalAmount.style.color = 'green';
	totalAmount.style.fontWeight = 'bold';
	totalAmount.style.fontSize = '1.3rem';
	totalAmount.parentElement.style.color = 'green';

	document.querySelectorAll('.btn-eliminar-prod-carrito').forEach(button => {
		button.addEventListener('click', mostrarModalConfirmacionEliminar);
	});
}

let productoAEliminarIndex;

function mostrarModalConfirmacionEliminar(event) {
	event.preventDefault();
	productoAEliminarIndex = event.target.closest('a').getAttribute('data-index');
	$('#confirmDeleteModal').modal('show');
}

function confirmarEliminarProducto() {
	carrito.splice(productoAEliminarIndex, 1);
	localStorage.setItem('carrito', JSON.stringify(carrito));
	actualizarCarrito();
	actualizarCarritoTable();
	$('#confirmDeleteModal').modal('hide');
}

fetch('/backend/productos/json', {
	method: 'GET',
	headers: {
		'Authorization': 'Bearer ' + token
	}
})
	.then(response => {
		if (!response.ok) {
			throw new Error('Error en la solicitud AJAX');
		}
		return response.json();
	})
	.then(data => {
		productos = data;
		return data;
	})
	.catch(error => {
		console.error('Error en la solicitud AJAX:', error);
	})





