package com.security.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.db.Rol;


public interface IRolRepository extends JpaRepository<Rol, Integer>{

    public Optional<Rol> findByNombre(String nombre);
    
}
