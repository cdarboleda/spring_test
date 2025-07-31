package com.security.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.Persona;
import com.security.db.Rol;
import com.security.exception.CustomException;
import com.security.service.IGestorPersonaService;
import com.security.service.IGestorProcesoService;
import com.security.service.IGestorUsurio;
import com.security.service.IKeycloakService;
import com.security.service.IPersonaService;
import com.security.service.IRolService;
import com.security.service.dto.MiProcesoDTO;
import com.security.service.dto.PersonaDTO;
import com.security.service.dto.utils.Convertidor;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GestorUsuarioImpl implements IGestorUsurio {

    @Autowired
    private IGestorPersonaService gestorPersonaService;

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IKeycloakService keycloakService;

    @Autowired
    private IGestorProcesoService gestorProcesoService;

    @Autowired
    private IRolService rolService;

    @Autowired
    private Convertidor convertidor;

    // Metodo para ingresar un nuevo registro de usuario
    @Override
    public PersonaDTO createUser(PersonaDTO personaDTO) {

        String idUser = this.keycloakService.createUser(
                personaDTO.getCedula(),
                personaDTO.getCorreo(),
                personaDTO.getRoles(),
                personaDTO.getNombre(),
                personaDTO.getApellido());

        personaDTO.setIdKeycloak(idUser);

        try {
            return this.gestorPersonaService.insertar(personaDTO);
        } catch (Exception e) {
            try {
                this.keycloakService.deleteUser(idUser);
            } catch (Exception rollbackError) {
                throw new RuntimeException(
                        "Error al insertar en la base de datos. Además, falló el rollback en Keycloak.", rollbackError);
            }
            throw new RuntimeException(
                    "Error al insertar en la base de datos. Se ha revertido la creación en Keycloak.", e);
        }
    }

    @Override
    public List<PersonaDTO> getUsers() {

        Rol rolBuscado = this.rolService.findAll().stream().filter(r -> r.getNombre().equals("administrador")).findAny()
                .orElse(null);

        List<PersonaDTO> personas = this.personaService.findAll().stream()
                .filter(p -> !p.getRoles().contains(rolBuscado))
                .map(this.convertidor::convertirAPersonaDTO)
                .collect(Collectors.toList());

        return personas;
    }

    @Override
    public PersonaDTO findUserByCedula(String cedula) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUserByCedula'");
    }

    @Override
    public Boolean updateUser(PersonaDTO personaDTO) {
        if (personaDTO == null || personaDTO.getId() == null) {
            throw new IllegalArgumentException("El objeto PersonaDTO o su ID no pueden ser nulos.");
        }

        Persona personaActual = this.personaService.findById(personaDTO.getId());
        if (personaActual == null) {
            throw new EntityNotFoundException("No se encontró la persona con id: " + personaDTO.getId());
        }

        boolean estaActiva = Boolean.TRUE.equals(personaActual.getActivo());
        boolean deseaActivar = Boolean.TRUE.equals(personaDTO.getActivo());
        boolean deseaDesactivar = Boolean.FALSE.equals(personaDTO.getActivo());

        // Validar desactivación con procesos activos
        if (estaActiva && deseaDesactivar) {
            if (tieneProcesosActivos(personaActual)) {
                throw new CustomException(
                        "La persona tiene procesos activos, no se puede desactivar.",
                        HttpStatus.CONFLICT);
            }
        }

        // Validar activación con roles administrativos ocupados
        if (!estaActiva && deseaActivar) {
            String rol = obtenerRolAdministrativo(personaDTO);
            if (!validarRolesAdministrativosDisponibles(personaDTO, rol)) {
                throw new CustomException(
                        "No se puede activar el usuario \"" + personaDTO.getNombre() + " " + personaDTO.getApellido() +
                                "\" mientras exista un usuario ACTIVO con ese rol.",
                        HttpStatus.CONFLICT);
            }
        }

        // Validar si hay cambios en roles y si están disponibles para asignar
        List<String> rolesActuales = personaActual.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toList());
        List<String> nuevosRoles = Optional.ofNullable(personaDTO.getRoles()).orElse(Collections.emptyList());
        if (Boolean.TRUE.equals(personaDTO.getActivo()) && !rolesActuales.containsAll(nuevosRoles)) {
            String rol = obtenerRolAdministrativo(personaDTO);
            if (!validarRolesAdministrativosDisponibles(personaDTO, rol)) {
                throw new CustomException(
                        "No se puede asignar el rol (" + rol + ") porque ya existe un usuario ACTIVO con ese rol.",
                        HttpStatus.CONFLICT);
            }
        }

        boolean requiereActualizarKeycloak = !Objects.equals(personaDTO.getCedula(), personaActual.getCedula()) ||
                !Objects.equals(personaDTO.getCorreo(), personaActual.getCorreo()) ||
                !Objects.equals(nuevosRoles, rolesActuales);

        boolean estado = true;

        if (requiereActualizarKeycloak) {
            estado = this.keycloakService.updateUser(
                    personaDTO.getIdKeycloak(),
                    personaDTO.getCedula(),
                    personaDTO.getCorreo(),
                    nuevosRoles);
        }

        if (estado) {
            this.gestorPersonaService.actualizar(personaDTO);
        }

        return estado;
    }

    @Override
    public Boolean deleteUser(String idKeycloak) {

        Persona personaActual = this.personaService.findByIdKeycloak(idKeycloak);
        if (personaActual == null) {
            throw new EntityNotFoundException("No se encontró la persona");
        }

        boolean tieneProcesosActivos = tieneProcesosActivos(personaActual);
        if (tieneProcesosActivos) {
            throw new CustomException("La persona tiene procesos activos, no se puede eliminar.", HttpStatus.CONFLICT);
        }

        String rol = obtenerRolAdministrativo(this.convertidor.convertirAPersonaDTO(personaActual));
        if (rol != null && personaActual.getActivo()) {
            throw new CustomException(
                    "No se puede eliminar el usuario con el rol (" + rol + ") mientras esté ACTIVO.",
                    HttpStatus.CONFLICT);
        }

        this.personaService.deleteByIdKeycloak(idKeycloak);
        // Si la eliminación en la base de datos fue exitosa, proceder con Keycloak
        boolean keycloakEliminado = this.keycloakService.deleteUser(idKeycloak);
        if (!keycloakEliminado) {
            throw new CustomException("Error al eliminar el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        System.out.println("Se eliminó de Keycloak exitosamente.");
        return keycloakEliminado;

    }

    private boolean tieneProcesosActivos(Persona persona) {
        List<MiProcesoDTO> procesos = this.gestorProcesoService.obtenerMisProcesos(persona.getId());

        if (procesos == null || procesos.isEmpty()) {
            return false;
        }

        for (MiProcesoDTO proceso : procesos) {
            // boolean cancelado = Boolean.TRUE.equals(proceso.getCancelado()); // seguro
            // contra null
            boolean finalizado = Boolean.FALSE.equals(proceso.getFinalizado());
            if (finalizado) {
                return true; // Si hay al menos un proceso no finalizado, retorna true
            }
        }
        return false;

    }

    private boolean validarRolesAdministrativosDisponibles(PersonaDTO personaDTO, String rolAdministrativo) {

        if (rolAdministrativo != null) {
            List<Persona> personas = this.gestorPersonaService.findPersonasByRol(rolAdministrativo);

            if (personas != null && !personas.isEmpty()) {
                boolean personalAdministrativoActivo = personas.stream()
                        .anyMatch(persona -> Boolean.TRUE
                                .equals(persona.getActivo() && persona.getId() != personaDTO.getId()));

                if (personalAdministrativoActivo) {
                    return false;
                }
            }
        }
        return true;
    }

    private String obtenerRolAdministrativo(PersonaDTO personaDTO) {
        List<String> rolesAdministrativos = List.of("secretaria", "director");
        return personaDTO.getRoles().stream()
                .filter(rolesAdministrativos::contains)
                .findAny()
                .orElse(null);
    }

}
