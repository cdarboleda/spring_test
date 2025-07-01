package com.security.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.Paso;
import com.security.db.Persona;
// import com.security.db.Rol;
import com.security.db.enums.Rol;
import com.security.exception.CustomException;
import com.security.repo.IPasoRepository;
import com.security.repo.IPersonaRepository;
import com.security.service.IGestorPersonaService;
import com.security.service.IPasoService;
import com.security.service.IPersonaService;
import com.security.service.dto.PersonaDTO;
import com.security.service.dto.utils.Convertidor;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GestorPersonaServiceImpl implements IGestorPersonaService {

    @Autowired
    private IPersonaRepository personaRepository;

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private Convertidor convertidor;

    @Autowired
    private IPasoService pasoService;

    @Autowired
    private IPasoRepository pasoRepository;

    @Override
    public PersonaDTO insertar(PersonaDTO personaDTO) {
        
        if (this.personaService.findByCedulaOptional(personaDTO.getCedula()).isPresent()) {
            throw new CustomException("Ya existe una persona con la cedula: " + personaDTO.getCedula(),
                    HttpStatus.BAD_REQUEST);
        }
        
        personaDTO.setId(null);
        Persona persona = new Persona();
        convertidor.convertirAPersona(persona, personaDTO);
        Set<Rol> roles = Rol.obtenerRolesFromNombre(personaDTO.getRoles());

        // this.rolesInvalidosMensaje(roles, personaDTO.getRoles());
        persona.setRoles(new HashSet<>(roles));
        return convertidor.convertirAPersonaDTO(personaRepository.save(persona));
    }

    @Override
    public Persona actualizar(PersonaDTO personaDTO) {
        Persona persona = this.personaService.findById(personaDTO.getId());
        Set<Rol> roles = Rol.obtenerRolesFromNombre(personaDTO.getRoles());
        persona.setRoles(new HashSet<>(roles));
        convertidor.convertirAPersona(persona, personaDTO);

        return persona;
    }

    @Override
    public void anadirPaso(Integer idPersona, Integer idPaso) {
        Persona responsable = this.personaService.findById(idPersona);
        Paso paso = this.pasoService.findById(idPaso);
        List<Paso> pasosActuales = responsable.getPasos();
        if (pasosActuales.stream().anyMatch((pasoActual) -> pasoActual.getId() == paso.getId())) {
            throw new CustomException("La persona ya es responsable del paso con id: " + idPaso,
                    HttpStatus.BAD_REQUEST);
        }

        pasosActuales.add(paso);
        responsable.setPasos(pasosActuales);
        paso.setResponsable(responsable);
    }

    @Override
    public List<Rol> findRolesByPersonaId(Integer id) {
        
        Persona persona = personaService.findById(id);

        Set<Rol> roles = persona.getRoles();
        if (roles.isEmpty()) {
            throw new EntityNotFoundException("No hay roles para persona con id " + id);
        }
        return new ArrayList<Rol>(roles);
    }

    @Override
    public List<Paso> findPasosByPersonaId(Integer id) {
        List<Paso> pasos = this.pasoRepository.findByResponsableId(id);
        if (pasos.isEmpty()) {
            throw new EntityNotFoundException("No hay pasos con responsable de id " + id);
        }
        return pasos;
    }

}
