package com.educacionit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.educacionit.dao.DAOInterface;
import com.educacionit.entity.HistorialCompras;
import com.educacionit.repository.HistorialComprasRepository;

@Service("historialComprasService")
public class HistorialComprasService implements DAOInterface<HistorialCompras> {

	@Autowired
	@Qualifier("historialComprasRepository")
	private HistorialComprasRepository historialComprasRepository;

	@Override
	public HistorialCompras save(HistorialCompras historialCompras) {
		return historialComprasRepository.save(historialCompras);
	}

	@Override
	public HistorialCompras getById(Integer id) {
		return historialComprasRepository.findById(id).orElse(null);
	}

	@Override
	public List<HistorialCompras> getAll() {
		return historialComprasRepository.findAll();
	}

	@Override
	public HistorialCompras update(Integer id, HistorialCompras historialComprasModificado) throws Exception {
	    Optional<HistorialCompras> existingHistorialComprasOptional = historialComprasRepository.findById(id);
	    
	    if (existingHistorialComprasOptional.isPresent()) {
	        HistorialCompras existingHistorialCompras = existingHistorialComprasOptional.get();
	        existingHistorialCompras.setCarrito(historialComprasModificado.getCarrito());
	        existingHistorialCompras.setCliente(historialComprasModificado.getCliente());
	        
	        return historialComprasRepository.save(existingHistorialCompras);
	    } else {
	        throw new Exception("El Historial de Compras con ID: " + id + " no Existe en la BD");
	    }
	}


	@Override
	public void delete(Integer id) {
		historialComprasRepository.deleteById(id);
	}
}
