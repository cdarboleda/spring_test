package com.security.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Persona;
import com.security.db.Rol;
import com.security.repo.IPersonaRepository;
import com.security.service.IPersonaService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PersonaServiceImpl implements IPersonaService{

    @Autowired
    private IPersonaRepository personaRepository;

    private final String entity ="Persona";

    @Override
    public List<Rol> findRolesByPersonId(Integer id) {
        if (!this.personaRepository.existsById(id)) { //No queria que use el findById ya que ese traeria todoo el objeto
            throw new EntityNotFoundException(this.entity);
        }
        List<Rol> roles = this.personaRepository.findRolesByPersonaId(id);
        if(roles.isEmpty()){
            throw new EntityNotFoundException("roles de "+this.entity);
        }
        return roles;
    }

    @Override
    public Optional<Persona> findByIdPerson(Integer id) {
        return this.personaRepository.findById(id);
    }

    @Override
    public Persona insert(Persona persona) {
        return this.personaRepository.save(persona);
    }

    @Override
    public List<Persona> findAll() {
        return this.personaRepository.findAll();
    }


    
    
}
