package com.educacionit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.educacionit.dao.DAOInterface;
import com.educacionit.entity.Categoria;
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
	public Categoria update(Integer id, Categoria categoriaModificada) throws Exception {
	    Optional<Categoria> existingCategoriaOptional = categoriaRepository.findById(id);
	    
	    if (existingCategoriaOptional.isPresent()) {
	        Categoria existingCategoria = existingCategoriaOptional.get();
	        existingCategoria.setNombre(categoriaModificada.getNombre());
	        existingCategoria.setDescripcion(categoriaModificada.getDescripcion());
	        
	        return categoriaRepository.save(existingCategoria);
	    } else {
	        throw new Exception("La Categoría con ID: " + id + " no Existe en la BD");
	    }
	}


	@Override
	public void delete(Integer id) {
		categoriaRepository.deleteById(id);
	}
	
	

}
