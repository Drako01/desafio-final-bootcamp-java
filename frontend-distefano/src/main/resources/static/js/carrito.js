function agregarProductoAlCarrito(producto) {
	const productoExistente = carrito.find(item => item.id === producto.id);
	if (productoExistente) {
		productoExistente.cantidad += producto.cantidad;
		productoExistente.subtotal += producto.subtotal;
	} else {
		carrito.push(producto);
	}

	localStorage.setItem('carrito', JSON.stringify(carrito));
	actualizarCarrito();
	actualizarCarritoTable();
}



botonComprarCarrito.addEventListener('click', () => {
	localStorage.setItem('carrito', '[]')
	location.reload();
})
