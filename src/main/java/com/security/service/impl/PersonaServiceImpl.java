package com.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Rol;
import com.security.repo.IPersonaRepository;
import com.security.service.IPersonaService;

@Service
public class PersonaServiceImpl implements IPersonaService{

    @Autowired
    private IPersonaRepository personaRepository;

    @Override
    public List<Rol> findPersonRoles(Integer id) {
        //Me devuelve los registros de personaRol donde el id matches
        var personaRoles = this.personaRepository.findPersonRoles(id);
        
        //Debo convertirlo en Rol
        personaRoles.stream().map((personaRol)->
        {
            //return el rol en cuestión
            return null;
        });
        return null;
    }
    
}
