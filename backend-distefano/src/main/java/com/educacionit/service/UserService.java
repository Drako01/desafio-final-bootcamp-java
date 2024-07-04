package com.educacionit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educacionit.dao.DAOInterface;
import com.educacionit.entity.User;
import com.educacionit.repository.UserRepository;

@Service("userService")
public class UserService implements DAOInterface<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(Integer id, User userModificado) throws Exception {
        Optional<User> existinguUserOptional = userRepository.findById(id);
        
        if (existinguUserOptional.isPresent()) {
            User existingUser = existinguUserOptional.get();
            existingUser.setNombre(userModificado.getNombre());
            existingUser.setUsername(userModificado.getUsername());
            existingUser.setPassword(userModificado.getPassword());
            existingUser.setTelefono(userModificado.getTelefono());
            
            return userRepository.save(existingUser);
        } else {
            throw new Exception("El Usuario con ID: " + id + " no Existe en la BD");
        }
    }


    @Override
    public void delete(Integer id) {
    	userRepository.deleteById(id);
    }
    
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
