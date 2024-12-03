package com.security.service.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Persona;
import com.security.repo.IPersonaRepository;
import com.security.service.IPersonaService;


@Service
public class PersonaServiceImpl implements IPersonaService {

    @Autowired
    private IPersonaRepository personaRepository;

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
