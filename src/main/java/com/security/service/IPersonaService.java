package com.security.service;

import java.util.List;
import java.util.Optional;

import com.security.db.Persona;

public interface IPersonaService {
    public Optional<Persona> findByIdPerson(Integer id);

    public Persona insert(Persona persona);

    public List<Persona> findAll();
}
