package com.security.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.Paso;
import com.security.db.Persona;
import com.security.db.Rol;
import com.security.exception.CustomException;
import com.security.repo.IPasoRepository;
import com.security.repo.IPersonaRepository;
import com.security.repo.IRolRepository;
import com.security.service.IGestorPersonaService;
import com.security.service.IPasoService;
import com.security.service.IPersonaService;
import com.security.service.IRolService;
import com.security.service.dto.PersonaDTO;
import com.security.service.dto.PersonaLigeroDTO;
import com.security.service.dto.utils.Convertidor;
import com.security.service.dto.utils.ConvertidorPersona;

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
    private IRolRepository rolRepository;

    @Autowired
    private IRolService rolService;

    @Autowired
    private Convertidor convertidor;

    @Autowired
    private ConvertidorPersona convertidorPersona;

    @Autowired
    private IPasoService pasoService;

    @Autowired
    private IPasoRepository pasoRepository;

    // Aqui busco si existe por la cedula, no hay path, solo body,
    // no importa que id ponga, no debe interesar(autoincremental), la cedula es lo
    // importante
    @Override
    public PersonaDTO insertar(PersonaDTO personaDTO) {
        // if (this.personaService.tieneErrores(personaDTO)) {
        // throw new CustomException("Hay campos erróneos, por favor revise antes de
        // enviar", HttpStatus.BAD_REQUEST);
        // }
        if (this.personaService.findByCedulaOptional(personaDTO.getCedula()).isPresent()) {
            throw new CustomException("Ya existe una persona con la cedula: " + personaDTO.getCedula(),
                    HttpStatus.BAD_REQUEST);
        }
        personaDTO.setId(null);
        Persona persona = new Persona();
        convertidor.convertirAPersona(persona, personaDTO);

        List<Rol> roles = this.rolService.findByNombreIn(personaDTO.getRoles());

        this.rolesInvalidosMensaje(roles, personaDTO.getRoles());

        persona.setRoles(new HashSet<>(roles));
        return convertidor.convertirAPersonaDTO(personaRepository.save(persona));
    }

    // Aqui busco si existe por el id, se supone que en el path pongo el id, no la
    // cedula
    // Aqui debo asegurarme que del front me llegue todos los campos, si no va a dar
    // error
    @Override
    public Persona actualizar(PersonaDTO personaDTO) {
        if (this.personaService.tieneErrores(personaDTO) || personaDTO.getId() == null) {
            throw new CustomException("Hay campos erróneos, por favor revise antes de enviar", HttpStatus.BAD_REQUEST);
        }
        Persona persona = this.personaService.findById(personaDTO.getId());
        List<Rol> roles = this.rolService.findByNombreIn(personaDTO.getRoles());

        this.rolesInvalidosMensaje(roles, personaDTO.getRoles());

        convertidor.convertirAPersona(persona, personaDTO);
        // personaDTOPersona.setProcesos(persona.getProcesos());
        persona.setRoles(new HashSet<>(roles));

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
        this.personaService.existsById(id);
        List<Rol> roles = this.rolRepository.findByPersonasId(id);
        if (roles.isEmpty()) {
            throw new EntityNotFoundException("No hay roles para persona con id " + id);
        }
        return roles;
    }

    @Override
    public List<Paso> findPasosByPersonaId(Integer id) {
        List<Paso> pasos = this.pasoRepository.findByResponsableId(id);
        if (pasos.isEmpty()) {
            throw new EntityNotFoundException("No hay pasos con responsable de id " + id);
        }
        return pasos;
    }

    @Override
    public List<PersonaLigeroDTO> findAllWithRoles() {
        List<PersonaLigeroDTO> dtos = this.personaRepository.findAllWithRoles().stream()
                .map(p -> new PersonaLigeroDTO(
                        p.getId(),
                        p.getCedula(),
                        p.getNombre(),
                        p.getApellido(),
                        p.getRoles().stream().map(Rol::getNombre).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return dtos;
    }

    private void rolesInvalidosMensaje(List<Rol> roles, List<String> rolesIds) {

        if (roles.size() != rolesIds.size()) {
            List<String> foundIds = roles.stream()
                    .map(Rol::getNombre)
                    .collect(Collectors.toList());

            List<String> missingIds = new ArrayList<>(rolesIds);
            missingIds.removeAll(foundIds);

            if (!missingIds.isEmpty()) {
                throw new CustomException("Los siguientes IDs de roles no existen: " + missingIds,
                        HttpStatus.BAD_REQUEST);
            }

        }

    }

    public PersonaLigeroDTO getDatosPersonaByEmail(String email) {
        Persona persona = this.personaService.findByEmail(email);
        return this.convertidorPersona.convertirALigeroDTO(persona);

    }

    @Override
    public List<Persona> findPersonasByRol(String nombreRol) {

        Optional<Rol> rol = this.rolService.buscarPorNombre(nombreRol);
        if (rol.isEmpty()) {
            throw new CustomException("No existe el rol con nombre: " + nombreRol, HttpStatus.BAD_REQUEST);
        } else {
            return new ArrayList<>(rol.get().getPersonas());
        }

    }

}
