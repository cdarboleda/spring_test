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
        personaDTO.setCedula(persona.getCedula());
        personaDTO.setCorreo(persona.getCorreo());
        personaDTO.setTelefono(persona.getTelefono());
        return personaDTO;
    }

    public Persona convertirAEntidad(Persona persona, PersonaLigeroDTO personaDTO) {
        persona.setId(personaDTO.getId());
        persona.setNombre(personaDTO.getNombre());
        persona.setApellido(personaDTO.getApellido());
        persona.setCedula(personaDTO.getCedula());
        persona.setCorreo(personaDTO.getCorreo());
        persona.setTelefono(personaDTO.getTelefono());
        return persona;
    }
}
