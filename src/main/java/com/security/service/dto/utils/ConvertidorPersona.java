package com.security.service.dto.utils;

import org.springframework.stereotype.Component;

import com.security.db.Persona;
import com.security.service.dto.PersonaLigeroDTO;

@Component
public class ConvertidorPersona {
    
    public PersonaLigeroDTO convertirALigeroDTO(Persona persona) {
        PersonaLigeroDTO personaDTO = new PersonaLigeroDTO();
        personaDTO.setId(persona.getId());
        personaDTO.setNombre(persona.getNombre());
        personaDTO.setApellido(persona.getApellido());
        personaDTO.setCorreo(persona.getCorreo());
        personaDTO.setTelefono(persona.getTelefono());
        personaDTO.setPassword(persona.getPassword());
        return personaDTO;
    }

    public Persona convertirAEntidad(PersonaLigeroDTO personaDTO) {
        Persona persona = new Persona();
        persona.setId(personaDTO.getId());
        persona.setNombre(personaDTO.getNombre());
        persona.setApellido(personaDTO.getApellido());
        persona.setCorreo(personaDTO.getCorreo());
        persona.setTelefono(personaDTO.getTelefono());
        persona.setPassword(personaDTO.getPassword());
        return persona;
    }
}
