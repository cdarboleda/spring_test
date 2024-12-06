package com.security.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.db.Persona;

@Repository
public interface IPersonaRepository extends JpaRepository<Persona, Integer>{

    public Optional<Persona> findByCedula(String cedula);

    
}
    
