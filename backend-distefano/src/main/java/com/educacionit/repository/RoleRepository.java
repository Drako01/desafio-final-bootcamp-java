package com.educacionit.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educacionit.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}