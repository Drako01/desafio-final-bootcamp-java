package com.educacionit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educacionit.dao.DAOInterface;
import com.educacionit.entity.Cliente;
import com.educacionit.repository.ClienteRepository;

@Service("clienteService")
public class ClienteService implements DAOInterface<Cliente> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente getById(Integer id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente update(Integer id, Cliente clienteModificado) throws Exception {
        Optional<Cliente> existingClienteOptional = clienteRepository.findById(id);
        
        if (existingClienteOptional.isPresent()) {
            Cliente existingCliente = existingClienteOptional.get();
            existingCliente.setNombre(clienteModificado.getNombre());
            existingCliente.setEmail(clienteModificado.getEmail());
            existingCliente.setPassword(clienteModificado.getPassword());
            existingCliente.setTelefono(clienteModificado.getTelefono());
            existingCliente.setLevel(clienteModificado.getLevel());
            existingCliente.setProductosFav(clienteModificado.getProductosFav());
            
            return clienteRepository.save(existingCliente);
        } else {
            throw new Exception("El Cliente con ID: " + id + " no Existe en la BD");
        }
    }


    @Override
    public void delete(Integer id) {
        clienteRepository.deleteById(id);
    }
    
    public Cliente getByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
}
