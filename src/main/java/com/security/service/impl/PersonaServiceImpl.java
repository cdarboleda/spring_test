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
import com.security.service.IGestorProcesoService;
import com.security.service.IPersonaService;
import com.security.service.dto.PersonaDTO;
import com.security.service.dto.PersonaLigeroDTO;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PersonaServiceImpl implements IPersonaService {

    @Autowired
    private IPersonaRepository personaRepository;

    @Override
    public Optional<Persona> findByIdOptional(Integer id) {
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
    public List<Persona> findAll() {
        return this.personaRepository.findAll();
    }

    @Override
    public List<PersonaLigeroDTO> findAllPersonaLigeroDTO() {
        return this.personaRepository.findAllPersonaLigeroDTO();
    }

    @Override
    public Optional<Persona> findByCedulaOptional(String cedula) {
        return this.personaRepository.findByCedula(cedula);
    }

    @Override
    public Persona findByIdKeycloak(String idKeycloak) {
        // return this.personaRepository.findByIdKeycloak(idKeycloak)
        // .orElseThrow(()-> new EntityNotFoundException("No se encontró un usuario con ese ID de keycloak"));
        return this.personaRepository.findByIdKeycloak(idKeycloak).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        // Persona persona = this.findById(id);
        this.personaRepository.deleteById(id);
    }

    // nomas revisa si estan nulos o vacio,
    // si por ejemplo se mandan letras en ves de numeros en roles, igual sale 400
    // pero sin mensaje personalziado pero no salta este if
    @Override
    public Boolean tieneErrores(PersonaDTO p) {
        return (p.getNombre() == null ||
                p.getApellido() == null ||
                p.getCedula() == null ||
                p.getCorreo() == null ||
                p.getRoles() == null ||
                p.getRoles().isEmpty()) ? true : false;
    }

    @Override
    public Persona findByEmail(String email) {

        return personaRepository.findByEmail(email);
    }


    @Override
    public boolean existeRegistro(String idKeycloak) {
        return personaRepository.existsByIdKeycloak(idKeycloak);
    }

    @Override
    public int deleteByIdKeycloak(String idKeycloak) {
        return this.personaRepository.deleteByIdKeycloak(idKeycloak);
    }

    @Override
    public Persona findByCedula(String cedula) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByCedula'");
    }

    @Override
    public List<Persona> findPersonasByIds(List<Integer> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findPersonasByIds'");
    }

}