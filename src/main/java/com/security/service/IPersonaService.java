package com.security.service;

import java.util.List;
import java.util.Optional;

import com.security.db.Persona;
import com.security.service.dto.PersonaDTO;

public interface IPersonaService {
    public Optional<Persona> findByIdOptional(Integer id);
    public Persona findByCedula(String cedula);
    public Optional<Persona> findByCedulaOptional(String cedula);
    public Persona findById(Integer id);
    public void deleteById(Integer id);
    public List<Persona> findAll();
    public Boolean existsById(Integer id);
    public List<Persona> findPersonasByIds(List<Integer> ids);
    public Boolean tieneErrores(PersonaDTO personaDTO);

    public int deleteByIdKeycloak(String idKeycloak);
    public boolean existeRegistro(String idKeycloak);
}
