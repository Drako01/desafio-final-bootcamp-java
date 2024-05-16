let botonComprar = document.querySelectorAll('.compar-btn');
let botonDetalles = document.querySelectorAll('.detalles-btn');
let productos = [];
let stockProd;
let productoId;


function guardarCarritoEnLocalStorage() {
	localStorage.setItem('carrito', JSON.stringify(carrito));
}


if (parseInt(stockProd) === 0) {
	botonComprar.innerText = 'Sin Stock';
	botonComprar.classList.remove('btn-success');
	botonComprar.classList.add('btn-info');
	botonComprar.disabled = true;
} else {
	botonComprar.forEach(btn => {
		btn.addEventListener('click', () => {
			let card = btn.closest('.card');
			let nombreProducto = card.querySelector('.card-title').innerText;
			let stockProducto = card.querySelector('.card-text:nth-child(3)').innerText.split(':')[1].trim();
			let idProducto = card.querySelector('.detalles-btn').getAttribute('href').split('/')[3];
			let precioProducto = parseFloat(card.querySelector('.card-text:nth-child(4)').innerText.split(':')[1].trim().replace('$', ''));

			let inputCantidad = document.createElement('input');
			inputCantidad.setAttribute('type', 'number');
			inputCantidad.setAttribute('min', '1');
			inputCantidad.setAttribute('max', parseInt(stockProducto));
			inputCantidad.setAttribute('value', '1');			
			inputCantidad.style.height = '36px';
			inputCantidad.style.borderRadius = '.25rem';

			let btnDetalles = card.querySelector('.detalles-btn');
			btnDetalles.parentNode.replaceChild(inputCantidad, btnDetalles);

			inputCantidad.addEventListener('change', () => {
				inputCantidad.setAttribute('max', parseInt(stockProducto));
			});

			
			let botonAgregar = document.createElement('button');
			botonAgregar.classList.add('btn', 'btn-success', 'agregar-btn');
			botonAgregar.innerHTML = 'Agregar <i class="fa fa-cart-plus"></i>';

			botonAgregar.addEventListener('click', () => {

				let cantidad = parseInt(inputCantidad.value);
				let subtotal = precioProducto * cantidad;
				let productoExistente = carrito.find(item => item.producto_id === idProducto && item.user_id === userId);

				if (productoExistente) {
					productoExistente.cantidad += cantidad;
					productoExistente.subtotal = productoExistente.cantidad * precioProducto;
				} else {
					carrito.push({
						user_id: userId,
						producto_id: idProducto,
						cantidad: cantidad,
						nombre: nombreProducto,
						precio: precioProducto,
						subtotal: subtotal
					});
				}

				guardarCarritoEnLocalStorage();
				actualizarCarrito();
				location.reload();
			});

			if (parseInt(stockProducto) === 0) {
				botonAgregar.innerText = 'Sin Stock';
				botonAgregar.classList.remove('btn-success');
				botonAgregar.classList.add('btn-warning');
				botonAgregar.disabled = true;
				inputCantidad.setAttribute('value', '0');
				inputCantidad.setAttribute('type', 'hidden');
							
			}


			btn.parentNode.replaceChild(botonAgregar, btn);
		});
	});
}