package com.security.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Persona;
import com.security.db.Rol;
import com.security.repo.IPersonaRepository;
import com.security.repo.IRolRepository;
import com.security.service.IGestorPersonaRol;
import com.security.service.dto.PersonaDTO;
import com.security.service.dto.utils.Convertidor;

import jakarta.transaction.Transactional;

@Service
public class GestorPersonaRolImpl implements IGestorPersonaRol {

    @Autowired
    private IPersonaRepository personaRepository;

    @Autowired
    private IRolRepository rolRepository;


    @Autowired
    private Convertidor convertidor;

    @Override
    @Transactional
    public PersonaDTO insertar(Persona persona, String rol) {
        if (persona == null || rol == null || rol.isEmpty()) {
            throw new IllegalArgumentException("Persona y rol no pueden ser nulos o vacíos");
        }
        
        

        Rol tmpRol = rolRepository.findByNombre(rol)
                .orElseThrow(() -> new RuntimeException("Rol " + rol + " no encontrado"));

        persona.setRoles(Collections.singletonList(tmpRol));

        System.out.println("-----------------------------------------------");
        System.out.println(tmpRol.getNombre());

        //System.out.println(persona.getRoles());

        

        return convertidor.convertirAPersonaDTO(personaRepository.save(persona), rol);
    }

    private boolean rolExiste(Integer idPersona, String rol){
        List<Rol> roles = this.personaRepository.findRolesByPersonaId(idPersona);
        return false;
    }

}
