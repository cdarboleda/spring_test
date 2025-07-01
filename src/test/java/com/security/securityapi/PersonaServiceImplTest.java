package com.security.securityapi;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.security.db.Persona;
import com.security.repo.IPersonaRepository;
import com.security.service.impl.PersonaServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)  
public class PersonaServiceImplTest {  
  
    @Mock  
    private IPersonaRepository personaRepository;  
  
    @InjectMocks  
    private PersonaServiceImpl personaService;  
  
    @Test  
    void findById_DeberiaRetornarPersona_CuandoExiste() {  
        // Given  
        Integer id = 1;  
        Persona persona = new Persona();  
        persona.setId(id);  
        persona.setNombre("Juan");  
          
        when(personaRepository.findById(id)).thenReturn(Optional.of(persona));  
  
        // When  
        Persona resultado = personaService.findById(id);  
  
        // Then  
        assertThat(resultado).isNotNull();  
        assertThat(resultado.getId()).isEqualTo(id);  
        assertThat(resultado.getNombre()).isEqualTo("Juan");  
    }  
  
    @Test  
    void findById_DeberiaLanzarExcepcion_CuandoNoExiste() {  
        // Given  
        Integer id = 999;  
        when(personaRepository.findById(id)).thenReturn(Optional.empty());  
  
        // When & Then  
        assertThatThrownBy(() -> personaService.findById(id))  
            .isInstanceOf(EntityNotFoundException.class)  
            .hasMessage("No se encontró la persona de id: " + id);  
    }  

}