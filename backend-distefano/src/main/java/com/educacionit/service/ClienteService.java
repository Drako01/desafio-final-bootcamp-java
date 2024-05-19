package com.educacionit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educacionit.dao.DAOInterface;
import com.educacionit.models.Cliente;
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
    public Cliente update(Integer id, Cliente cliente) {
        Cliente existeCliente = clienteRepository.findById(id).orElse(null);
        if (existeCliente != null) {
            existeCliente.setNombre(cliente.getNombre());
            existeCliente.setEmail(cliente.getEmail());
            existeCliente.setTelefono(cliente.getTelefono());
            existeCliente.setProductosFav(cliente.getProductosFav());
            
            return clienteRepository.save(existeCliente);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        clienteRepository.deleteById(id);
    }
}
