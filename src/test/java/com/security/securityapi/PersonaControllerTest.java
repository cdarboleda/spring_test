package com.security.securityapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.controller.PersonaController;
import com.security.service.IGestorPersonaService;
import com.security.service.IGestorProcesoService;
import com.security.service.IGestorUsurio;
import com.security.service.IPersonaService;
import com.security.service.dto.PersonaDTO;
@WebMvcTest(
    controllers = PersonaController.class,
    // Esto evita cargar automáticamente todas las configuraciones
    excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = com.security.config.SecurityConfig.class)
)
@ContextConfiguration(classes = {
    PersonaController.class,
    PersonaControllerTest.TestSecurityConfig.class
})
public class PersonaControllerTest {

    @TestConfiguration
    @EnableWebSecurity
    static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean 
    private IPersonaService personaService;

    @MockitoBean 
    private IGestorProcesoService gestorProcesoService;

    @MockitoBean 
    private IGestorUsurio gestorUsuarioService;

    @MockitoBean 
    private IGestorPersonaService gestorPersonaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void insertar_DeberiaRetornarBadRequest_CuandoFaltanCampos() throws Exception {
        PersonaDTO personaInvalida = new PersonaDTO();
        personaInvalida.setNombre("Juan");
        personaInvalida.setApellido("Pérez");

        mockMvc.perform(post("/persona")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personaInvalida)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void insertar_DeberiaRetornarOk_CuandoDTOValido() throws Exception {
        PersonaDTO personaValida = new PersonaDTO();
        personaValida.setNombre("Juan");
        personaValida.setApellido("Pérez");
        personaValida.setCorreo("juan@correo.com");
        personaValida.setTelefono("0999999999");
        personaValida.setCedula("1234567890");
        personaValida.setActivo(true);
        personaValida.setRoles(List.of("DOCENTE"));

        mockMvc.perform(post("/persona")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personaValida)))
                .andExpect(status().isOk());
    }
}
