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
        dto.setCedula(persona.getCedula());
        dto.setCorreo(persona.getCorreo());
        dto.setTelefono(persona.getTelefono());
        //dto.setRol(rol);
        
        return dto;

    }

    public Persona convertirAPersona(Persona persona, PersonaDTO dto){
        persona.setId(dto.getId());
        persona.setNombre(dto.getNombre());
        persona.setApellido(dto.getApellido());
        persona.setCedula(dto.getCedula());
        persona.setCorreo(dto.getCorreo());
        persona.setTelefono(dto.getTelefono());
        
        return persona;

    }
    
}
