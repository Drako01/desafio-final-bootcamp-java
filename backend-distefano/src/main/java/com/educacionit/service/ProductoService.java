package com.educacionit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.educacionit.dao.DAOInterface;
import com.educacionit.entity.Producto;
import com.educacionit.repository.ProductoRepository;

@Service("productoService")
public class ProductoService implements DAOInterface<Producto> {

	@Autowired
	@Qualifier("productoRepository")
	private ProductoRepository productoRepository;

	@Override
	public Producto save(Producto producto) {
		return productoRepository.save(producto);
	}

	@Override
	public Producto getById(Integer id) {
		Producto unProducto = productoRepository.findById(id).orElse(null);
		return unProducto;
	}

	@Override
	public List<Producto> getAll() {
		return productoRepository.findAll();
	}

	@Override
	public Producto update(Integer id, Producto productoModificado) throws Exception {
	    Optional<Producto> existingProductoOptional = productoRepository.findById(id);
	    
	    if (existingProductoOptional.isPresent()) {
	        Producto existingProducto = existingProductoOptional.get();
	        existingProducto.setNombre(productoModificado.getNombre());
	        existingProducto.setDescripcion(productoModificado.getDescripcion());
	        existingProducto.setPrecio(productoModificado.getPrecio());
	        existingProducto.setImagen(productoModificado.getImagen());
	        existingProducto.setStock(productoModificado.getStock());
	        existingProducto.setCategoria(productoModificado.getCategoria());
	        
	        return productoRepository.save(existingProducto);
	    } else {
	        throw new Exception("El Producto con ID: " + id + " no Existe en la BD");
	    }
	}


	@Override
	public void delete(Integer id) {
		productoRepository.deleteById(id);
	}
}