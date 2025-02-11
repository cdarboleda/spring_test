package com.security.service.dto.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.security.db.Persona;
import com.security.service.dto.PersonaDTO;

@Component
public class Convertidor {

    public PersonaDTO convertirAPersonaDTO(Persona persona) {
        PersonaDTO dto = new PersonaDTO();
        dto.setId(persona.getId());
        dto.setIdKeycloak(persona.getIdKeycloak());
        dto.setNombre(persona.getNombre());
        dto.setApellido(persona.getApellido());
        dto.setCedula(persona.getCedula());
        dto.setCorreo(persona.getCorreo());
        dto.setTelefono(persona.getTelefono());
        List<String> nombreRoles = persona.getRoles()
                .stream()
                .map(rol -> rol.getNombre())
                .collect(Collectors.toList());

        dto.setRoles(nombreRoles);
        dto.setActivo(persona.getActivo());

        return dto;

    }

    public Persona convertirAPersona(Persona persona, PersonaDTO dto){
        persona.setId(dto.getId());
        persona.setIdKeycloak(dto.getIdKeycloak());
        persona.setNombre(dto.getNombre());
        persona.setApellido(dto.getApellido());
        persona.setCedula(dto.getCedula());
        persona.setCorreo(dto.getCorreo());
        persona.setTelefono(dto.getTelefono());
        persona.setActivo(dto.getActivo());
        
        return persona;

    }
    
}
