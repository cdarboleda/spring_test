package com.security.service;

import java.util.List;

import com.security.db.Persona;
import com.security.db.Rol;
import com.security.service.dto.PersonaDTO;

public interface IGestorPersonaRol {

    public Persona insertar(PersonaDTO persona);
    public Persona actualizar(PersonaDTO persona);
    public List<Rol> findRolesByPersonaId(Integer id);
}
