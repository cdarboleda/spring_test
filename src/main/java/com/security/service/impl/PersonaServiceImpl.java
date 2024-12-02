package com.security.service.impl;


import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import com.security.db.Persona;
import com.security.db.Proceso;
import com.security.db.Rol;
import com.security.repo.IPersonaRepository;
import com.security.service.IPersonaService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PersonaServiceImpl implements IPersonaService {

    @Autowired
    private IPersonaRepository personaRepository;

    @Override
    public List<Rol> findRolesByPersonId(Integer id) {
        return findEntitiesByPersonId(id, this.personaRepository::findRolesByPersonaId, "roles");
    }

    @Override
    public List<Proceso> findProcesosWherePersonaIsOwner(Integer id) {
        return findEntitiesByPersonId(id, this.personaRepository::findProcesosWherePersonaIsOwner, "procesos");
    }

    @Override
    public List<Proceso> findProcesosByPersonaId(Integer id) {
        return findEntitiesByPersonId(id, this.personaRepository::findProcesosByPersonaId, "procesos");
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

    private <T> List<T> findEntitiesByPersonId(Integer id, Function<Integer, List<T>> queryFunction,
            String subEntityName) {
        if (!this.personaRepository.existsById(id)) {
            throw new EntityNotFoundException("No hay persona con id: " + id);
        }
        List<T> entities = queryFunction.apply(id);
        if (entities.isEmpty()) {
            throw new EntityNotFoundException("No hay " + subEntityName + " para persona con id " + id);
        }
        return entities;
    }

}
