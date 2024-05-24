package com.educacionit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.educacionit.dao.DAOInterface;
import com.educacionit.entity.Item;
import com.educacionit.repository.ItemRepository;

@Service("itemService")
public class ItemService implements DAOInterface<Item> {
	@Autowired
    @Qualifier("itemRepository")
    private ItemRepository itemRepository;

	@Override
	public Item save(Item item) {
		return itemRepository.save(item);
	}

	@Override
	public Item getById(Integer id) {
		return itemRepository.findById(id).orElse(null);
	}

	@Override
	public List<Item> getAll() {
		return itemRepository.findAll();
	}

	@Override
	public Item update(Integer id, Item itemModificado) throws Exception {
Optional<Item> existingItemOptional = itemRepository.findById(id);
        
        if (existingItemOptional.isPresent()) {
        	Item existingItem = existingItemOptional.get();
        	existingItem.setProducto(itemModificado.getProducto());;
        	existingItem.setCantidad(itemModificado.getCantidad());
        	existingItem.setPrecio_unitario(itemModificado.getPrecio_unitario());
        	existingItem.setCarrito(itemModificado.getCarrito());
        	existingItem.setId_item(itemModificado.getId_item());
            
            return itemRepository.save(existingItem);
        } else {
            throw new Exception("El Item con ID: " + id + " no Existe en la BD");
        }
	}

	@Override
	public void delete(Integer id) {
		itemRepository.deleteById(id);		
	}

}
