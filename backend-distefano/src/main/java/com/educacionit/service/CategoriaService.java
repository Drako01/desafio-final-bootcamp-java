package com.educacionit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.educacionit.dao.DAOInterface;
import com.educacionit.models.Categoria;
import com.educacionit.repository.CategoriaRepository;

@Service("categoriaService")
public class CategoriaService implements DAOInterface<Categoria> {

	@Autowired
	@Qualifier("categoriaRepository")
	private CategoriaRepository categoriaRepository;

	@Override
	public Categoria save(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	@Override
	public Categoria getById(Integer id) {
		return categoriaRepository.findById(id).orElse(null);
	}

	@Override
	public List<Categoria> getAll() {
		return categoriaRepository.findAll();
	}

	@Override
	public Categoria update(Integer id, Categoria categoria) {
		Categoria existeCategoria = categoriaRepository.findById(id).orElse(null);
		if (existeCategoria != null) {
			existeCategoria.setNombre(categoria.getNombre());
			existeCategoria.setDescripcion(categoria.getDescripcion());
			return categoriaRepository.save(categoria);
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		categoriaRepository.deleteById(id);
	}
	
	

}
