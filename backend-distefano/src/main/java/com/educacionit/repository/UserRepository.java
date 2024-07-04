package com.educacionit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educacionit.entity.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {	
	Optional<User> findByUsername(String username);
}
