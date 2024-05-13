package com.educacionit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.educacionit.dao.DAOInterface;
import com.educacionit.models.Producto;
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
	public Producto update(Integer id, Producto producto) {
		Producto existingProducto = productoRepository.findById(id).orElse(null);
		if (existingProducto != null) {
			existingProducto.setId_producto(producto.getId_producto());
			existingProducto.setNombre(producto.getNombre());
			existingProducto.setImagen(producto.getImagen());;
			existingProducto.setDescripcion(producto.getDescripcion());
			existingProducto.setPrecio(producto.getPrecio());
			existingProducto.setStock(producto.getStock());;
			existingProducto.setCategoria(producto.getCategoria());
			return productoRepository.save(existingProducto);
		} else {
			return null;
		}
	}

	@Override
	public void delete(Integer id) {
		productoRepository.deleteById(id);
	}
}