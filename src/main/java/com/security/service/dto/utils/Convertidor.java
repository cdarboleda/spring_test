package com.security.service.dto.utils;

import org.springframework.stereotype.Component;

import com.security.db.Persona;
import com.security.service.dto.PersonaDTO;

@Component
public class Convertidor {

    public PersonaDTO convertirAPersonaDTO(Persona persona, String rol){
        PersonaDTO dto = new PersonaDTO();
        dto.setId(persona.getId());
        dto.setNombre(persona.getNombre());
        dto.setApellido(persona.getApellido());
        dto.setCorreo(persona.getCorreo());
        dto.setPassword(persona.getPassword());
        dto.setTelefono(persona.getTelefono());
        dto.setRol(rol);
        
        return dto;

    }

    public Persona convertirAPersona(PersonaDTO dto){
        Persona persona = new Persona();
        persona.setId(dto.getId());
        persona.setNombre(dto.getNombre());
        persona.setApellido(dto.getApellido());
        persona.setCorreo(dto.getCorreo());
        persona.setPassword(dto.getPassword());
        persona.setTelefono(dto.getTelefono());
        
        return persona;

    }
    
}
