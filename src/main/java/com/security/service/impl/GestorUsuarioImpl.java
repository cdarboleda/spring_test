package com.security.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.security.service.IGestorPersonaService;
import com.security.service.IGestorUsurio;
import com.security.service.IKeycloakService;
import com.security.service.IPersonaService;
import com.security.service.dto.PersonaDTO;
import com.security.service.dto.utils.Convertidor;

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
    private Convertidor convertidor;

    // Metodo para ingresar un nuevo registro de usuario
    @Override
    public PersonaDTO createUser(PersonaDTO personaDTO) {

        System.out.println("personaDTooooooooooooooooooooooO: " + personaDTO);
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
            PersonaDTO dto = this.convertidor.convertirAPersonaDTO(this.gestorPersonaService.actualizar(personaDTO));

            System.out.println(dto);

            return this.keycloakService.updateUser(
                    dto.getIdKeycloak(),
                    dto.getCedula(),
                    dto.getCorreo(),
                    dto.getRoles());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteUser(String idKeycloak) {
        try {
            // Intentar eliminar en la base de datos
            int bdEliminado = this.personaService.deleteByIdKeycloak(idKeycloak);
            System.out.println("Eliminados en la base de datos: " + bdEliminado);

            // Si no se eliminó ningún registro en la base de datos, retornar false
            if (bdEliminado == 0) {
                System.out.println("No se encontró el registro en la base de datos para eliminar.");
                return false;
            }

            // Verificar manualmente si el registro sigue existiendo en la tabla para evitar
            // errores post-transacción
            boolean sigueExistiendo = this.personaService.existeRegistro(idKeycloak);
            if (sigueExistiendo) {
                System.err.println("El registro no se pudo eliminar porque está siendo referenciado en otras tablas.");
                return false;
            }

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
