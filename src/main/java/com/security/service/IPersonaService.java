package com.security.service;

import java.util.List;
import java.util.Optional;

import com.security.db.Persona;
import com.security.db.Proceso;
import com.security.db.Rol;

public interface IPersonaService {
    public List<Rol> findRolesByPersonId(Integer id);

    public List<Proceso> findProcesosWherePersonaIsOwner(Integer id);

    public List<Proceso> findProcesosByPersonaId(Integer id);

    public Optional<Persona> findByIdPerson(Integer id);

    public Persona insert(Persona persona);

    public List<Persona> findAll();
}
