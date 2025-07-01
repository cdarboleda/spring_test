package com.security.securityapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.security.service.IGestorPersonaService;
import com.security.service.IKeycloakService;
import com.security.service.dto.PersonaDTO;
import com.security.service.impl.GestorUsuarioImpl;

@ExtendWith(MockitoExtension.class)
public class GestorUsuarioImplTest {

    @Mock
    private IKeycloakService keycloakService;

    @Mock
    private IGestorPersonaService gestorPersonaService;

    @InjectMocks
    private GestorUsuarioImpl gestorUsuario;

    @Test
    void createUser_DeberiaCrearUsuarioEnKeycloakYGuardarEnBD() {
        // Given
        PersonaDTO personaDTO = new PersonaDTO();
        personaDTO.setNombre("Juan");
        personaDTO.setApellido("Pérez");
        personaDTO.setCedula("1234567890");
        personaDTO.setCorreo("juan@example.com");
        personaDTO.setTelefono("0999999999");
        personaDTO.setActivo(true);
        personaDTO.setRoles(List.of("DOCENTE"));

        String userId = "keycloak-123";

        when(keycloakService.createUser(
            personaDTO.getCedula(),
            personaDTO.getCorreo(),
            personaDTO.getRoles(),
            personaDTO.getNombre(),
            personaDTO.getApellido()
        )).thenReturn(userId);

        PersonaDTO personaConIdKeycloak = new PersonaDTO();
        personaConIdKeycloak.setIdKeycloak(userId);
        when(gestorPersonaService.insertar(any(PersonaDTO.class))).thenReturn(personaConIdKeycloak);

        // When
        PersonaDTO resultado = gestorUsuario.createUser(personaDTO);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdKeycloak()).isEqualTo(userId);

        verify(keycloakService).createUser(
            personaDTO.getCedula(),
            personaDTO.getCorreo(),
            personaDTO.getRoles(),
            personaDTO.getNombre(),
            personaDTO.getApellido()
        );
        verify(gestorPersonaService).insertar(any(PersonaDTO.class));
    }
}
