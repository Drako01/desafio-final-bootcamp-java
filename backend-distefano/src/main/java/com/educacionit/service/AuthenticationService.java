package com.educacionit.service;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.educacionit.dto.LoginDTO;
import com.educacionit.dto.RegistracionDTO;
import com.educacionit.entity.Role;
import com.educacionit.entity.User;
import com.educacionit.repository.RoleRepository;
import com.educacionit.repository.UserRepository;

@Service
public class AuthenticationService {
	
	private static final Integer ID_ROLE = 2; //ROLE_USER

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public User registrar(RegistracionDTO registracion) {
        try {
            User unUsuario = new User();
            unUsuario.setNombre(registracion.getFullName());
            unUsuario.setEmail(registracion.getEmail());
            
            unUsuario.setPassword(
                    passwordEncoder.encode(registracion.getPassword()));
           
            Optional<Role> optionalRole = roleRepository.findById(ID_ROLE);
            if (!optionalRole.isPresent()) {
                throw new IllegalArgumentException("El rol no existe.");
            }
            Role unRole = optionalRole.get();
            
            unRole.setId(ID_ROLE);
            Set<Role> defaultRole = new HashSet<>();            
            defaultRole.add(unRole);
            
            unUsuario.setRoles(defaultRole);
            
            User usuarioGuardado = userRepository.save(unUsuario);
            Optional<User> usuarioConRoles = userRepository.findById(usuarioGuardado.getId());
            
            if(!usuarioConRoles.isPresent()) {
                return null;
            }
            return usuarioConRoles.get();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("El correo electrónico ya está en uso.");
        }
    }
	
	
	public User autenticar(LoginDTO login) {
		
		authenticationManager.
				authenticate(new UsernamePasswordAuthenticationToken(
							login.getEmail(), 
							login.getPassword())
						);
		return userRepository.findByEmail(login.getEmail()).orElse(null);
	}
	
	
}
