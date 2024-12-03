package com.security.service.impl;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Proceso;
import com.security.repo.IPersonaRepository;
import com.security.repo.IProcesoRepository;
import com.security.service.IGestorPersonaProceso;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GestorPersonaProceso implements IGestorPersonaProceso{

    @Autowired
    private IProcesoRepository procesoRepository;
    @Autowired
    private IPersonaRepository personaRepository;

    @Override
    public List<Proceso> findProcesosByPersonaId(Integer id) {
        return findEntitiesByPersonId(id, this.procesoRepository::findProcesosByPersonaId, "procesos");
    }

    @Override
    public List<Proceso> findProcesosWherePersonaIsOwner(Integer id) {
        return findEntitiesByPersonId(id, this.procesoRepository::findProcesosWherePersonaIsOwner, "procesos propietario");
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
