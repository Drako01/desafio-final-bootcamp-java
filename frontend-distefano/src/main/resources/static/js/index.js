window.onload = function() {
	if (window.location.pathname === '/backend/categorias/') {
		inicializarEventoCategoria();
	} else if (window.location.pathname === '/backend/productos/') {
		inicializarEventoProducto();
	}
	obtenerFechaYHora();
};

const fechaHoraElement = document.getElementById('fechaHora');
$(document).ready(function() {
  $('[data-toggle="modal"]').modal();
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