package com.security.securityapi;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.security.db.Persona;
import com.security.repo.IPersonaRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonaRepositoryTest {

    @Autowired
    private IPersonaRepository personaRepository;

    @Test
    void guardarYBuscarPersona_DeberiaPersistirYRecuperarCorrectamente() {
        
        // Given
        Persona persona = new Persona();
        persona.setNombre("Juan");
        persona.setApellido("Pérez");
        persona.setCedula("1234567890");
        persona.setCorreo("juan@correo.com");
        persona.setTelefono("0999999999");
        persona.setActivo(true);

        // When
        personaRepository.save(persona);
        Optional<Persona> resultado = personaRepository.findById(persona.getId());

        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Juan");
    }
}