package com.security.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.Persona;
import com.security.db.Paso;
import com.security.db.Rol;
import com.security.exception.CustomException;
import com.security.repo.IPersonaRepository;
import com.security.repo.IRolRepository;
import com.security.service.IGestorPersonaRol;
import com.security.service.IPasoService;
import com.security.service.IPersonaService;
import com.security.service.dto.PersonaDTO;
import com.security.service.dto.utils.Convertidor;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GestorPersonaRolImpl implements IGestorPersonaRol {

    @Autowired
    private IPersonaRepository personaRepository;

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IRolRepository rolRepository;

    @Autowired
    private Convertidor convertidor;

    @Autowired
    private IPasoService pasoService;

    @Override
    public Persona insertar(PersonaDTO personaDTO) {
        if (personaDTO == null) {
            throw new IllegalArgumentException("Persona y rol no pueden ser nulos o vacíos");
        }

        Optional<Persona> personaExistente = personaRepository.findByCedula(personaDTO.getCedula());
        if (personaExistente.isPresent()) {
            throw new IllegalArgumentException("Ya existe una persona con la cédula: " + personaDTO.getCedula());
        }
        personaDTO.setId(null);

        Set<Rol> roles = personaDTO.getRoles()
                .stream()
                .map((idRol) -> this.rolRepository.findById(idRol).get())
                .filter(Objects::nonNull)// .filter(rol -> rol!=null)
                .collect(Collectors.toSet());

        Persona persona = convertidor.convertirAPersona(personaDTO);
        persona.setRoles(roles);
        return personaRepository.save(persona);
    }

    // El front me debe dar los datos completos si o si
    @Override
    public Persona actualizar(PersonaDTO personaDTO) {
        if (personaDTO == null) {
            throw new IllegalArgumentException("Persona y rol no pueden ser nulos o vacíos");
        }
        Optional<Persona> persona = this.personaRepository.findById(personaDTO.getId());
        if (!persona.isPresent()) {
            throw new IllegalArgumentException("Esta persona no existe!!!, SOLO actualizar");
        }
        Persona personaDTOPersona = convertidor.convertirAPersona(personaDTO);

        Set<Rol> roles = personaDTO.getRoles()
                .stream()
                .map((idRol) -> this.rolRepository.findById(idRol).get())
                .filter(Objects::nonNull)// .filter(rol -> rol!=null)
                .collect(Collectors.toSet());

        personaDTOPersona.setId(persona.get().getId());
        personaDTOPersona.setProcesos(persona.get().getProcesos());
        personaDTOPersona.setPersonasProceso(persona.get().getPersonasProceso());
        personaDTOPersona.setRoles(roles);

        return personaRepository.save(personaDTOPersona);
    }
    @Override
    public void anadirPaso(Integer idPersona, Integer idPaso){
        Persona responsable = this.personaService.findById(idPersona);
        Paso paso = this.pasoService.findById(idPaso);
        Set<Paso> pasosActuales = responsable.getPasos();
        if(pasosActuales.stream().anyMatch((pasoSet)->pasoSet.getId()==paso.getId())){
            throw new CustomException("La persona ya es responsable del paso con id: "+idPaso, HttpStatus.BAD_REQUEST);
        }

        pasosActuales.add(paso);
        responsable.setPasos(pasosActuales);
        paso.setResponsable(responsable);
    }
    

    @Override
    public List<Rol> findRolesByPersonaId(Integer id) {
        this.personaService.existsById(id);
        List<Rol> roles = this.rolRepository.findByPersonasId(id);
        if (roles.isEmpty()) {
            throw new EntityNotFoundException("No hay roles para persona con id " + id);
        }
        return roles;
    }

    @Override
    public Persona addRolToPersona(PersonaDTO dto) {
        Optional<Persona> personaOpt = personaRepository.findByCedula(dto.getCedula());

        if (personaOpt.isEmpty()) {
            throw new RuntimeException("Persona no encontrada con la cédula: " + dto.getCedula());
        }

        Persona persona = personaOpt.get();

        Set<Rol> roles = rolRepository.findAllById(dto.getRoles())
                .stream()
                .collect(Collectors.toSet());

        if (roles.isEmpty()) {
            throw new RuntimeException("No se encontraron roles para los IDs proporcionados");
        }

        persona.getRoles().addAll(roles);

        return personaRepository.save(persona);
    }

}
