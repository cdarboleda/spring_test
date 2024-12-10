package com.security.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.security.db.Persona;

public interface IPersonaService {
    public Optional<Persona> findByIdPerson(Integer id);
    public Persona findByCedula(String cedula);
    public Persona findById(Integer id);
    public Persona insert(Persona persona);


    public List<Persona> findAll();
    public Boolean existsById(Integer id);
    public List<Persona> findPersonasByIds(List<Integer> ids);
}
