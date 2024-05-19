let botonComprar = document.querySelectorAll('.compar-btn');
let botonDetalles = document.querySelectorAll('.detalles-btn');
let productos = [];
let stockProd;
let productoId;
let imagenUrl;

function guardarCarritoEnLocalStorage() {
	localStorage.setItem('carrito', JSON.stringify(carrito));
}
let parrafoStockCero = document.querySelectorAll('.stock-p');
parrafoStockCero.forEach(parrafo => {
	let stockProducto = parseInt(parrafo.innerText.split(':')[1].trim());
	if (stockProducto === 0) {
		parrafo.style.color = 'red';
		let btnComprar = parrafo.closest('.card').querySelector('.compar-btn');
		btnComprar.classList.add('disabled');
		btnComprar.innerText = 'Sin Stock';
		btnComprar.classList.remove('btn-success');
		btnComprar.classList.add('btn-warning');
	}
});

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
			let imagenProducto = card.querySelector('.card-img-top').src;
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
						subtotal: subtotal,
                        imagen: imagenProducto
					});
				}

				guardarCarritoEnLocalStorage();
				actualizarCarrito();
				location.reload();
			});

			if (parseInt(stockProducto) === 0) {
				inputCantidad.setAttribute('value', '0');
				inputCantidad.setAttribute('type', 'hidden');

			}


			btn.parentNode.replaceChild(botonAgregar, btn);
		});
	});
}