package com.security.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.Persona;
import com.security.db.Proceso;
import com.security.exception.CustomException;
import com.security.repo.IPersonaRepository;
import com.security.repo.IProcesoRepository;
import com.security.service.IGestorProceso;
import com.security.service.IPersonaService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PersonaServiceImpl implements IPersonaService {

    @Autowired
    private IPersonaRepository personaRepository;
    @Autowired
    private IProcesoRepository procesoRepository;

    @Override
    public Optional<Persona> findByIdPerson(Integer id) {
        return this.personaRepository.findById(id);
    }

    @Override
    public Persona findById(Integer id) {
        Persona persona = this.personaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la persona de id: " + id));
        return persona;
    }

    @Override
    public Boolean existsById(Integer id) {
        if (!this.personaRepository.existsById(id)) {
            throw new EntityNotFoundException("No hay persona con id: " + id);
        }
        return true;
    }

    @Override
    public Persona insert(Persona persona) {
        return this.personaRepository.save(persona);
    }

    @Override
    public List<Persona> findAll() {
        return this.personaRepository.findAll();
    }

    @Override
    public List<Persona> findPersonasByIds(List<Integer> ids) {
        List<Persona> personas;
        try {
            personas = personaRepository.findAllById(ids);
        } catch (Exception e) {
            throw new CustomException("No se encontraron personas con los IDs proporcionados.", HttpStatus.BAD_REQUEST);
        }

        if (personas.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron personas con los IDs proporcionados.");
        }
        return personas;
    }

    @Override
    public Persona findByCedula(String cedula) {
        return this.personaRepository.findByCedula(cedula)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro la persona con cedula: " + cedula));
    }

    @Override
    public void deleteById(Integer id) {
        Persona persona = this.findById(id);        
        // persona.getPersonasProceso().forEach(proceso -> {
            
        //     proceso.getPersonas().remove(persona);
        // });
        // persona.getPersonasProceso().clear();
  
        this.personaRepository.deleteById(id);
    }

}
