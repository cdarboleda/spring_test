package com.security.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.security.db.Persona;
import com.security.service.IGestorPersonaService;
import com.security.service.IGestorProcesoService;
import com.security.service.IGestorUsurio;
import com.security.service.IKeycloakService;
import com.security.service.IPersonaService;
import com.security.service.dto.MiProcesoDTO;
import com.security.service.dto.PersonaDTO;
import com.security.service.dto.ProcesoDTO;
import com.security.service.dto.utils.Convertidor;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GestorUsuarioImpl implements IGestorUsurio {

    private final PersonaServiceImpl personaServiceImpl;

    private final ProcesoServiceImpl procesoServiceImpl;

    @Autowired
    private IGestorPersonaService gestorPersonaService;

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IKeycloakService keycloakService;

    @Autowired
    private IGestorProcesoService gestorProcesoService;

    @Autowired
    private Convertidor convertidor;

    GestorUsuarioImpl(ProcesoServiceImpl procesoServiceImpl, PersonaServiceImpl personaServiceImpl) {
        this.procesoServiceImpl = procesoServiceImpl;
        this.personaServiceImpl = personaServiceImpl;
    }

    // Metodo para ingresar un nuevo registro de usuario
    @Override
    public PersonaDTO createUser(PersonaDTO personaDTO) {

        String idUser = this.keycloakService.createUser(
                personaDTO.getCedula(),
                personaDTO.getCorreo(),
                personaDTO.getRoles());

        if (idUser == null) {
            throw new RuntimeException("Error al crear el usuario en Keycloak");
        }

        personaDTO.setIdKeycloak(idUser);
        try {
            return this.gestorPersonaService.insertar(personaDTO);
        } catch (Exception e) {
            try {
                this.keycloakService.deleteUser(idUser); // Intentar eliminar en Keycloak
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

        List<PersonaDTO> personas = this.personaService.findAll().stream()
                .map(persona -> this.convertidor.convertirAPersonaDTO(persona)).collect(Collectors.toList());

        return personas;
    }

    @Override
    public PersonaDTO findUserByCedula(String cedula) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUserByCedula'");
    }

    @Override
    public Boolean updateUser(PersonaDTO personaDTO) {
        try {
            Persona personaActual = this.personaService.findById(personaDTO.getId());
            if (personaActual == null) {
                throw new EntityNotFoundException("No se encontró la persona con id: " + personaDTO.getId());
            }

            // Validar si están intentando desactivar y la persona tiene procesos activos
            if (personaActual.getActivo() && !personaDTO.getActivo()) {
                boolean tieneProcesosActivos = tieneProcesosActivos(personaActual);
                if (tieneProcesosActivos) {
                    return false;
                }
            }

            // Actualizar la persona en la base de datos
            System.out.println("Actualizando persona en la base de datos...");
            Persona personaActualizada = this.gestorPersonaService.actualizar(personaDTO);
            System.out.println("se actualizo");
            // Convertir para extraer los datos necesarios
            System.out.println("Convertir para extraer los datos necesarios...");
            PersonaDTO dtoActualizada = this.convertidor.convertirAPersonaDTO(personaActualizada);
            System.out.println("se convirtio a DTO");
            // Actualizar en Keycloak

            boolean keycloakActualizado = this.keycloakService.updateUser(
                    personaActual.getIdKeycloak(),
                    dtoActualizada.getCedula(),
                    dtoActualizada.getCorreo(),
                    dtoActualizada.getRoles());
            System.out.println("se actualizo en keycloak");
            return keycloakActualizado;

        } catch (EntityNotFoundException | IllegalStateException e) {
            System.err.println("error en la base de datos" + e.getMessage());
            return false;
        } catch (Exception e) {
            // Errores inesperados
            System.err.println(" Error inesperado al actualizar la persona: " + e.getMessage());
            return false;
        }
    }

    private boolean tieneProcesosActivos(Persona persona) {
        // TODO Auto-generated method stub
        List<MiProcesoDTO> procesos = this.gestorProcesoService.obtenerMisProcesos(persona.getId());

        for (MiProcesoDTO miProcesoDTO : procesos) {
            System.out.println(miProcesoDTO.toString());
        }
        for (MiProcesoDTO proceso : procesos) {
            boolean cancelado = Boolean.TRUE.equals(proceso.getCancelado()); // seguro contra null
            boolean finalizado = Boolean.TRUE.equals(proceso.getFinalizado());
            if (!cancelado || !finalizado) {
                return true;
            }
        }
        return false;

    }

    @Override
    public Boolean deleteUser(String idKeycloak) {
        try {
            Persona personaActual = this.personaService.findByIdKeycloak(idKeycloak);
            boolean tieneProcesosActivos = tieneProcesosActivos(personaActual);
            if (tieneProcesosActivos) {
                System.out.println("La persona tiene procesos activos, no se puede eliminar.");
                return false;
            }
            this.personaService.deleteByIdKeycloak(idKeycloak);
            // Si la eliminación en la base de datos fue exitosa, proceder con Keycloak
            boolean keycloakEliminado = this.keycloakService.deleteUser(idKeycloak);
            System.out.println("Se eliminó de Keycloak exitosamente.");
            return keycloakEliminado;

        } catch (DataIntegrityViolationException e) {
            // Capturar errores de integridad referencial
            System.err.println("Error de integridad referencial al eliminar en la base de datos: " + e.getMessage());
            return false;

        } catch (Exception e) {
            // Capturar cualquier otra excepción
            System.err.println("Error inesperado: " + e.getMessage());
            return false;
        }
    }
}
