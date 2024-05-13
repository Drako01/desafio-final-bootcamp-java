window.onload = function() {
	if (window.location.pathname === '/backend/categorias/') {
		inicializarEventoCategoria();
	} else if (window.location.pathname === '/backend/productos/') {
		inicializarEventoProducto();
	}
};